Nápověda pro variantu s relační databází MSSQL
##############################################################
V souboru file.properties lze nalézt tyto konfigurace:
	username= nastavení uživatelského jména k databázi.
	password= nastavení hesla k databázi.
	url= nastavení URL k databázi.
	databaseName= nastavení jména databáze.
	generatePathsFile= nastavení hodnoty true/false, zda se má generovat mezisoubor.
	inputXML= nastavení cesty vstupního XML (funguje jen pro MySQL).
	outputPath= nastavení cesty mezisouboru.
	inputQuery= nastavení vstupního SQL dotazu, ke kterému se mají spočítat podobné SQL dotazy. 
	functionsForSimilarity= nastavení funkcí pro SQL. Každá funkce musí být oddělená čárkou a mezerou (tzn. ", "). 
		Slouží pro lepší výstup podobností SQL dotazů s danými funkcemi (záleží na situaci, někdy je to neúčinné). 
		Je zde nutné nastavit ty samé funkce, které byly nastaveny v generátoru souborů. 
		K této konfiguraci byl přidán základní výčet funkcí viz příklad níže.

##############################################################
Pro správné fungování je nutné všechny konfigurace (a cesty) nastavit.
Pozor na lomítka v absolutních cestách. Pokud se zadá absolutní cesta s opačným lomítkem, vyvolá se výjimka.
V databázi je nutné vytvořit určitou tabulku, jejíž skript pro vytvoření se nachází ve složce s přílohama /db_varianta/skript.sql
Varianta s databází funguje zatím jen pro MySQL.

Příklad:
	username=sol0095
	password=password
	url=jdbc:sqlserver://dbsys.cs.vsb.cz\\STUDENT;
	databaseName=sol0095
	generatePathsFile=true
	inputXML=data/MySQL.xml
	outputPath=data/outputPaths.txt
	inputQuery=SELECT * FROM products WHERE (price BETWEEN 1.0 AND 2.0) AND (quantity BETWEEN 1000 AND 2000)
	functionsForSimilarity=JOIN, GROUP BY, ORDER BY, MAX, MIN, SUM, COUNT, ASC, AVG, DESC, AND, OR, =, <=, >=, !=, >, <, <>, NULL, IN, \
	LIKE, ANY, ALL, BETWEEN, EXISTS, SOME, REGEXP, CONCAT, CONCAT_WS, LEFT, LEN, LOWER, REPLACE, REVERSE, RIGHT, STR, SUBSTRING, \
	UPPER, POWER, SQRT, EXP, ROUND, RAND, DATEADD, DATEDIFF, DATEPART, DAY, GETDATE, MONTH, YEAR, CAST, ISNULL, COALESCE, \
	DATALENGTH, CHARINDEX, DIFFERENCE, FORMAT, LTRIM, NCHAR, PATINDEX, QUOTENAME, REPLICATE, RTRIM, SOUNDEX, SPACE, STUFF, \
	TRANSLATE, TRIM, UNICODE, ABS, SQUARE, RADIANS, PI, LOG10, LOG, FLOOR, DEGREES, CEILING, CURRENT_TIMESTAMP, DATEFROMPARTS, \
	DATENAME, GETUTCDATE, ISDATE, SYSDATETIME, SYSDATE, SYSTIMESTAMP, CONVERT, IIF, ISNUMERIC, NULLIF, USER_NAME, SESSIONPROPERTY, FROM_UNIXTIME, \
	HEX, STRFTIME, DATE, IFNULL, INSTR, TIME, STR_TO_DATE, INITCAP, ASCII, LPAD, RPAD, SUBSTR, REGEXP_REPLACE, REGEXP_SUBSTR, \
	REGEXP_INSTR, CURRENT_DATE, EXTRACT, MONTHS_BETWEEN, TO_TIMESTAMP, TO_CHAR, TRUNC, UNISTR, TO_NUMBER, TO_NCHAR, TO_DATE, PRINTF
