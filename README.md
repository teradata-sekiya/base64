# base64関数作成プロジェクト
このプロジェクトは、Teradata UDF でBase64 のエンコードとデコードを行うため関数作成のためのプロジェクトです。  
この関数は以下のサイズのデータを扱えます。
- バーイナリーデータ 64,000 Byte
- Base64文字データ 64,000 文字

## (1)Jarfileを作成する
Javaソースプログラムをコンパイルして任意のフォルダにjarファイルをエクスポートします。  
こちらの例では、"C:\temp"の下にjarファイルを出力しています。  
最新のコンパイル済みJarファイルは、**本プロジェクト内のDistフォルダ**にあります。

	C:/temp/base64.jar

## (2)Teradataのdbcにログインします

	.logon dbc,{パスワード}

## (3)UDF権限を付与
	GRANT EXECUTE PROCEDURE ON sqlj to {ターゲットユーザ};
	GRANT FUNCTION  ON {ターゲットユーザ} TO {ターゲットユーザ};

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


## (6)UDF関数の実行方法。
### (6.1)Base64文字列をデコードします
	select b64dec({Base64文字列});

### (6.2)Base64文字列にエンコードします
	select b64enc({バイナリーデータ});
