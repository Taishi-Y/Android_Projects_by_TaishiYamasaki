![login](http://i.imgur.com/vUs2s7o.png)
![main screen](http://i.imgur.com/iaNsjS0.png)

#Overview(概要)
ニフティクラウド(mBaas)のAndroidサンプルです。このサンプルではログイン/ログアウト/サインアップ/ポストなどマイクロポスト型のブログに必要な機能を含んでいます。
Androidでニフティクラウドを利用してまずは小さなサービスを作ってみたいという個人の開発者のためになればうれしいです。
***
This is the sample project for nifty cloud mobile backend with Android.(NCMB)
NCMB　is mBaas which is very similar to Parse.( https://www.parse.com/ )
This sample project is including login/logout/signup/(post).
I will be glad if I can help beginner Android developer (I'm also super beginner. lol) who want to create own apps.


#Usage(使い方)
まず、ニフティクラウド( http://mb.cloud.nifty.com/ )のサイトからユーザー登録を済ませてAPP_KEYとCLIENT_KEYを取得してください。
APP_KEYとCLIENT_KEYを取得できたらこのプロジェクトののInitialActivityのAPP_KEYとCLIENT_KEYをご自身のものに書き換えてください。

```java:InitialActivity
    //You have to change these strings to yours.
    public static final String APP_KEY = "YOUR_APP_KEY";
    public static final String CLIENT_KEY = "YOUR_CLIENT_KEY";
```
***
First you have to register NCMB which is free to get APP_KEY and CLIENT_KEY.
Then you just change APP_KEY and CLIENT_KEY to yours.
