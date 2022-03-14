# base64関数作成プロジェクト
このプロジェクトは、Teradata UDF でBase64 のエンコードとデコードを行うため関数作成のためのプロジェクトです。

## (1)Jarfileを作成する
プロジェクトに含まれるJavaソースプログラムをコンパイルして以下のようなフォルダにjarファイルをエクスポートします。
最新のコンパイル済みJarファイルはDistフォルダにあります。

	C:/temp/base64.jar

## (2)Teradataのdbcにログインします
	.logon dbc,{パスワード}

## (3)UDF作成権限を付与
	grant EXECUTE PROCEDURE on sqlj to {ターゲットユーザ};

## (4)TeradataのUDFを実行するユーザにログインします
	.logon {ターゲットユーザ},{パスワード}

## (5)JarfileをDBにインポートする
	CALL SQLJ.INSTALL_JAR('CJ!C:/temp/base64.jar', 'b64', 0); 

## (6)UDF関数を定義する
	replace FUNCTION b64dec(p1 long VARCHAR CHARACTER SET LATIN)
	RETURNS varbyte(64000)
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64:com.teradata.b64.dec(java.lang.String) returns byte[]';
    
	replace FUNCTION b64enc(p1 Blob)
	RETURNS VARCHAR CHARACTER SET LATIN
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64:com.teradata.b64.enc(java.sql.Blob) returns java.lang.String';


## (6)UDF関数を実行します。
### (6.1)Base64文字列をデコードします
	select b64dec({Base64文字列});

### (6.2)Base64文字列にエンコードします
	select b64enc({バイナリーデータ});
