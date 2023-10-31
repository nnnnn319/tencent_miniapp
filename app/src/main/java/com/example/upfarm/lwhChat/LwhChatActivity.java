package com.example.upfarm.lwhChat;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upfarm.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;
import java.util.List;

public class LwhChatActivity {

    MediaRecordingUtils mediaRecordingUtils;
    //音乐地址
    String filePath;
    //音乐播放器
    MediaPlayer mediaPlayer;
    public LwhChatActivity(){
        mediaRecordingUtils=new MediaRecordingUtils();
        mediaRecordingUtils.setOnAudioStatusUpdateListener(new MediaRecordingUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(String filePath1) {
                filePath=filePath1;
            }
        });
    }


    //注册
    public void register(String username,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, password);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //发送语音消息,callback函数处理发送成功后的需求,返回本地uri和网络url
    public void sendVoiceMessage(String filePath,int length,String username,Context context){
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length,username);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qq","语音消息发送成功");
                EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
                //获取语音文件在服务器的地址
                String voiceRemoteUrl = voiceBody.getRemoteUrl();
                Log.e("qq",voiceRemoteUrl);
                //本地语音文件的资源路径
                Uri voiceLocalUri = voiceBody.getLocalUri();
                isPlayOrPauseLocal(voiceLocalUri.toString(),context);
                Log.e("qq",voiceLocalUri.toString());
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qq","消息发送失败"+","+i+","+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    //发送文本消息,callback函数处理发送成功后的需求
    public void sendTextMessage(String text,String username){
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(text, username);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qq","消息发送成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qq","消息发送失败"+","+i+","+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    //登陆
    public void login(String username,String password){
        Log.e("执行","执行");
        //登陆
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qq","登陆成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qq","登陆失败"+","+i+","+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    //登出
    public void logout(){
        //登陆
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qq","退出成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qq","退出失败"+","+i+","+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    //播放指定本地的音乐
    public void isPlayOrPauseLocal(String filePath, Context context){
        //播放内存卡中音频文件

        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
            //设置类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            //设置音源
            try {

                mediaPlayer.setDataSource(context, Uri.parse("file://mnt"+filePath));
                //准备一下(内存卡)
                mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            Log.e("music","ddddd");
            mediaPlayer=null;

        }else if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    //播放指定网络url的音乐
    public void isPlayOrPauseNetwork(String URL,Context context){
        if(mediaPlayer==null){
            //实例化MediaPlayer
            mediaPlayer=new MediaPlayer();
            //设置类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            //设置音源
            try {
                //播放网络音乐
                mediaPlayer.setDataSource(context, Uri.parse(URL));

                //准备（网络）
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //监听：准备完成的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

            mediaPlayer= null;

        }else if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }


    //接收到别人发送的信息后进行的回调
//    @Override
//    public void onResume() {
//        super.onResume();
//        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
//            @Override
//            public void onMessageReceived(List<EMMessage> list) {
//                for (EMMessage message:list) {
//                    Log.e("qq接收消息成功",message.getFrom());
//                    if (message.getType()== EMMessage.Type.VOICE){
//                        String url = ((EMVoiceMessageBody)message.getBody()).getRemoteUrl();
//                        int  length = ((EMVoiceMessageBody)message.getBody()).getLength();
//                        String fromUser = message.getFrom();
//                        Log.e("qq接收消息成功",url+","+length+","+fromUser);
//                    }else if (message.getType()== EMMessage.Type.TXT){
//                        String text=((EMTextMessageBody) message.getBody()).getMessage();
//                        Log.e("qq接收文本消息成功",text);
//                    }else if (message.getType()== EMMessage.Type.IMAGE){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCmdMessageReceived(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageRead(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageDelivered(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageRecalled(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageChanged(EMMessage emMessage, Object o) {
//
//            }
//        });
//    }
}