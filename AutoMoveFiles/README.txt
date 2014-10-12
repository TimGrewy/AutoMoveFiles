Execute by running command: java -jar AutoMoveFiles.jar

Requirements:
* settings.xml in the same directory as the jar file
** First argument after AutoMoveFiles.jar can bu used to set a location for settings.xml


Export to jar:
Export til runable jar: Eclipse->File->Export->Runnable jar - Choose Main.java to be default class



Example of settings.xml:
<Properties>
	<sourceFolder>C:\Source</sourceFolder>
	<patternsFile>rules.xml</patternsFile>
	<logFile>log.txt</logFile>
	<deleteIfExists>true</deleteIfExists>
	<deleteIfNoMatch>false</deleteIfNoMatch>
</Properties>

Example of a rules.xml:
<Rules>
	<Rule>
		<Pattern>.*?regex.+?name.*?</Pattern>
		<Path>\\Destinatation\DestinationFolder</Path>
	</Rule>
	<Rule>
		<Pattern>.*?regex.+?name.*?</Pattern>
		<Path>\\Destinatation\DestinationFolder</Path>
		<PutInSeasons>true</PutInSeasons>
	</Rule>
</Rules>
* Put in seasons will extract the season from the filename: E.g.: S01E10 -> "Season 01"