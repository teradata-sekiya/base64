# base64関数作成プロジェクト
このプロジェクトは、Teradata UDF でBase64 のエンコードとデコードを行うため関数作成のためのプロジェクトです。  
この関数は以下のサイズのデータを扱えます。
- Blob型のバーイナリーデータ 5MB
- Clob型のBase64文字データ 5MB

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

## (5)JarfileをDBからリムーブする。(6)未実施の場合はスキップしてください。
	CALL SQLJ.REMOVE_JAR('b64', 0); 

## (6)JarfileをDBにインポートする

	CALL SQLJ.INSTALL_JAR('CJ!C:/temp/base64.jar', 'b64', 0); 

## (7)UDF関数を定義する

	replace FUNCTION b64dec(p1 Clob(5m))
	RETURNS Blob(5m)
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64:com.teradata.b64.dec(java.sql.Clob) returns java.sql.Blob';


	replace FUNCTION b64enc(p1 Blob(5m))
	RETURNS Clob(5m)
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64:com.teradata.b64.enc(java.sql.Blob) returns java.sql.Clob';


## (8)UDF関数の実行方法。
### (8.1)Base64文字列をデコードします
	select b64dec({Clob型のBase64文字});

### (8.2)Base64文字列にエンコードします
	select b64enc({Blob型のバイナリ});
