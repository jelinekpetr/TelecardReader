
drop table if exists TEL_KARTY;

create table IF NOT EXISTS TEL_KARTY 
(
rec_id int not null AUTO_INCREMENT,
catalog varchar(20),
catalog_number varchar(20),
card_serial varchar(50),
chip_serial varchar(50),
country varchar(3),
country_extra varchar(100),
state varchar(100),
manufacturer varchar(100),
issuer varchar(100),
content longtext,
content_hash varchar(128),
date_add timestamp default CURRENT_TIMESTAMP,
who_add varchar(100),
note varchar(200),
primary key (rec_id)
);

ALTER TABLE `TEL_KARTY` ADD INDEX ( `catalog` );
ALTER TABLE `TEL_KARTY` ADD INDEX ( `country` );
ALTER TABLE `TEL_KARTY` ADD UNIQUE ( `content_hash` );

drop table if exists CISEL_ZEMI;

create table if not exists CISEL_ZEMI (
num_kod varchar(3) not null,
kod_2 varchar(2),
kod_3 varchar(3),
cs_dl varchar(200),
cs_kr varchar(100),
en_dl varchar(200),
en_kr varchar(100),
primary key (num_kod)
);



Insert into CISEL_ZEMI values ("004","AF","AFG","Islámská republika Afghánistán","Afghánistán","the Islamic Republic of Afghanistan","Afghanistan");
Insert into CISEL_ZEMI values ("008","AL","ALB","Albánská republika","Albánie","the Republic of Albania","Albania");
Insert into CISEL_ZEMI values ("010","AQ","ATA","Antarktida","Antarktida","Antarctica","Antarctica");
Insert into CISEL_ZEMI values ("012","DZ","DZA","Alžírská demokratická a lidová republika","Alžírsko","the People's Democratic Republic of Algeria","Algeria");
Insert into CISEL_ZEMI values ("016","AS","ASM","Americká Samoa","Americká Samoa","American Samoa","American Samoa");
Insert into CISEL_ZEMI values ("020","AD","AND","Andorrské knížectví","Andorra","the Principality of Andorra","Andorra");
Insert into CISEL_ZEMI values ("024","AO","AGO","Angolská republika","Angola","the Republic of Angola","Angola");
Insert into CISEL_ZEMI values ("028","AG","ATG","Antigua a Barbuda","Antigua a Barbuda","Antigua and Barbuda","Antigua and Barbuda");
Insert into CISEL_ZEMI values ("031","AZ","AZE","Ázerbájdžánská republika","Ázerbájdžán","the Republic of Azerbaijan","Azerbaijan");
Insert into CISEL_ZEMI values ("032","AR","ARG","Argentinská republika","Argentina","the Argentine Republic","Argentina");
Insert into CISEL_ZEMI values ("036","AU","AUS","Austrálie","Austrálie","Australia","Australia");
Insert into CISEL_ZEMI values ("040","AT","AUT","Rakouská republika","Rakousko","the Republic of Austria","Austria");
Insert into CISEL_ZEMI values ("044","BS","BHS","Bahamské společenství","Bahamy","the Commonwealth of The Bahamas","Bahamas (The)");
Insert into CISEL_ZEMI values ("048","BH","BHR","Království Bahrajn","Bahrajn","the Kingdom of Bahrain","Bahrain");
Insert into CISEL_ZEMI values ("050","BD","BGD","Bangladéšská lidová republika","Bangladéš","the People's Republic of Bangladesh","Bangladesh");
Insert into CISEL_ZEMI values ("051","AM","ARM","Arménská republika","Arménie","the Republic of Armenia","Armenia");
Insert into CISEL_ZEMI values ("052","BB","BRB","Barbados","Barbados","Barbados","Barbados");
Insert into CISEL_ZEMI values ("056","BE","BEL","Belgické království","Belgie","the Kingdom of Belgium","Belgium");
Insert into CISEL_ZEMI values ("060","BM","BMU","Bermudy","Bermudy","Bermuda","Bermuda");
Insert into CISEL_ZEMI values ("064","BT","BTN","Bhútánské království","Bhútán","the Kingdom of Bhutan","Bhutan");
Insert into CISEL_ZEMI values ("068","BO","BOL","Mnohonárodní stát Bolívie","Bolívie","the Plurinational State of Bolivia","Bolivia, Plurinational State of");
Insert into CISEL_ZEMI values ("070","BA","BIH","Bosna a Hercegovina","Bosna a Hercegovina","Bosnia and Herzegovina","Bosnia and Herzegovina");
Insert into CISEL_ZEMI values ("072","BW","BWA","Republika Botswana","Botswana","the Republic of Botswana","Botswana");
Insert into CISEL_ZEMI values ("074","BV","BVT","Bouvetův ostrov","Bouvetův ostrov","Bouvet Island","Bouvet Island");
Insert into CISEL_ZEMI values ("076","BR","BRA","Brazilská federativní republika","Brazílie","the Federative Republic of Brazil","Brazil");
Insert into CISEL_ZEMI values ("084","BZ","BLZ","Belize","Belize","Belize","Belize");
Insert into CISEL_ZEMI values ("086","IO","IOT","Britské indickooceánské území","Britské indickooceánské území","British Indian Ocean Territory (the)","British Indian Ocean Territory (the)");
Insert into CISEL_ZEMI values ("090","SB","SLB","Šalomounovy ostrovy","Šalomounovy ostrovy","Solomon Islands (the)","Solomon Islands (the)");
Insert into CISEL_ZEMI values ("092","VG","VGB","Britské Panenské ostrovy","Britské Panenské ostrovy","British Virgin Islands (the)","Virgin Islands (British)");
Insert into CISEL_ZEMI values ("095","XK","XKO","Republika Kosovo","Kosovo","","");
Insert into CISEL_ZEMI values ("096","BN","BRN","Brunej Darussalam","Brunej Darussalam","Brunei Darussalam","Brunei Darussalam");
Insert into CISEL_ZEMI values ("100","BG","BGR","Bulharská republika","Bulharsko","the Republic of Bulgaria","Bulgaria");
Insert into CISEL_ZEMI values ("104","MM","MMR","Republika Myanmarský svaz","Myanma","the Republic of the Union of Myanmar","Myanmar");
Insert into CISEL_ZEMI values ("108","BI","BDI","Republika Burundi","Burundi","the Republic of Burundi","Burundi");
Insert into CISEL_ZEMI values ("112","BY","BLR","Běloruská republika","Bělorusko","the Republic of Belarus","Belarus");
Insert into CISEL_ZEMI values ("116","KH","KHM","Kambodžské království","Kambodža","the Kingdom of Cambodia","Cambodia");
Insert into CISEL_ZEMI values ("120","CM","CMR","Kamerunská republika","Kamerun","the Republic of Cameroon","Cameroon");
Insert into CISEL_ZEMI values ("124","CA","CAN","Kanada","Kanada","Canada","Canada");
Insert into CISEL_ZEMI values ("132","CV","CPV","Kapverdská republika","Kapverdy","the Republic of Cape Verde","Cape Verde");
Insert into CISEL_ZEMI values ("136","KY","CYM","Kajmanské ostrovy","Kajmanské ostrovy","Cayman Islands (the)","Cayman Islands (the)");
Insert into CISEL_ZEMI values ("140","CF","CAF","Středoafrická republika","Středoafrická republika","the Central African Republic","Central African Republic (the)");
Insert into CISEL_ZEMI values ("144","LK","LKA","Srílanská demokratická socialistická republika","Srí Lanka","the Democratic Socialist Republic of Sri Lanka","Sri Lanka");
Insert into CISEL_ZEMI values ("148","TD","TCD","Čadská republika","Čad","the Republic of Chad","Chad");
Insert into CISEL_ZEMI values ("152","CL","CHL","Chilská republika","Chile","the Republic of Chile","Chile");
Insert into CISEL_ZEMI values ("156","CN","CHN","Čínská lidová republika","Čína","the People's Republic of China","China");
Insert into CISEL_ZEMI values ("158","TW","TWN","Tchaj-wan (čínská provincie)","Tchaj-wan (čínská provincie)","Taiwan (Province of China)","Taiwan (Province of China)");
Insert into CISEL_ZEMI values ("162","CX","CXR","Vánoční ostrov","Vánoční ostrov","Christmas Island","Christmas Island");
Insert into CISEL_ZEMI values ("166","CC","CCK","Kokosové (Keelingovy) ostrovy","Kokosové (Keelingovy) ostrovy","Cocos (Keeling) Islands (the)","Cocos (Keeling) Islands (the)");
Insert into CISEL_ZEMI values ("170","CO","COL","Kolumbijská republika","Kolumbie","the Republic of Colombia","Colombia");
Insert into CISEL_ZEMI values ("174","KM","COM","Komorský svaz","Komory","the Union of the Comoros","Comoros");
Insert into CISEL_ZEMI values ("175","YT","MYT","Mayotte","Mayotte","Mayotte","Mayotte");
Insert into CISEL_ZEMI values ("178","CG","COG","Konžská republika","Kongo, republika","the Republic of the Congo","Congo");
Insert into CISEL_ZEMI values ("180","CD","COD","Demokratická republika Kongo","Kongo, demokratická republika","the Democratic Republic of the Congo","Congo (the Democratic Republic of the)");
Insert into CISEL_ZEMI values ("184","CK","COK","Cookovy ostrovy","Cookovy ostrovy","Cook Islands (the)","Cook Islands (the)");
Insert into CISEL_ZEMI values ("188","CR","CRI","Kostarická republika","Kostarika","the Republic of Costa Rica","Costa Rica");
Insert into CISEL_ZEMI values ("191","HR","HRV","Chorvatská republika","Chorvatsko","the Republic of Croatia","Croatia");
Insert into CISEL_ZEMI values ("192","CU","CUB","Kubánská republika","Kuba","the Republic of Cuba","Cuba");
Insert into CISEL_ZEMI values ("196","CY","CYP","Kyperská republika","Kypr","the Republic of Cyprus","Cyprus");
Insert into CISEL_ZEMI values ("203","CZ","CZE","Česká republika","Česká republika","the Czech Republic","Czech Republic (the)");
Insert into CISEL_ZEMI values ("204","BJ","BEN","Beninská republika","Benin","the Republic of Benin","Benin");
Insert into CISEL_ZEMI values ("208","DK","DNK","Dánské království","Dánsko","the Kingdom of Denmark","Denmark");
Insert into CISEL_ZEMI values ("212","DM","DMA","Dominické společenství","Dominika","the Commonwealth of Dominica","Dominica");
Insert into CISEL_ZEMI values ("214","DO","DOM","Dominikánská republika","Dominikánská republika","the Dominican Republic","Dominican Republic (the)");
Insert into CISEL_ZEMI values ("218","EC","ECU","Ekvádorská republika","Ekvádor","the Republic of Ecuador","Ecuador");
Insert into CISEL_ZEMI values ("222","SV","SLV","Salvadorská republika","Salvador","the Republic of El Salvador","El Salvador");
Insert into CISEL_ZEMI values ("226","GQ","GNQ","Republika Rovníková Guinea","Rovníková Guinea","the Republic of Equatorial Guinea","Equatorial Guinea");
Insert into CISEL_ZEMI values ("231","ET","ETH","Etiopská federativní demokratická republika","Etiopie","the Federal Democratic Republic of Ethiopia","Ethiopia");
Insert into CISEL_ZEMI values ("232","ER","ERI","Eritrea","Eritrea","Eritrea","Eritrea");
Insert into CISEL_ZEMI values ("233","EE","EST","Estonská republika","Estonsko","the Republic of Estonia","Estonia");
Insert into CISEL_ZEMI values ("234","FO","FRO","Faerské ostrovy","Faerské ostrovy","Faroe Islands (the)","Faroe Islands (the)");
Insert into CISEL_ZEMI values ("238","FK","FLK","Falklandské ostrovy (Malvíny)","Falklandské ostrovy (Malvíny)","Falkland Islands (the) (Malvinas)","Falkland Islands (the) (Malvinas)");
Insert into CISEL_ZEMI values ("239","GS","SGS","Jižní Georgie a Jižní Sandwichovy ostrovy","Jižní Georgie a Jižní Sandwichovy ostrovy","South Georgia and the South Sandwich Islands","South Georgia and the South Sandwich Islands");
Insert into CISEL_ZEMI values ("242","FJ","FJI","Fidžijská republika","Fidži","the Republic of Fiji","Fiji");
Insert into CISEL_ZEMI values ("246","FI","FIN","Finská republika","Finsko","the Republic of Finland","Finland");
Insert into CISEL_ZEMI values ("248","AX","ALA","Alandské ostrovy","Alandské ostrovy","Åland Islands","Åland Islands");
Insert into CISEL_ZEMI values ("250","FR","FRA","Francouzská republika","Francie","the French Republic","France");
Insert into CISEL_ZEMI values ("254","GF","GUF","Francouzská Guyana","Francouzská Guyana","French Guiana","French Guiana");
Insert into CISEL_ZEMI values ("258","PF","PYF","Francouzská Polynésie","Francouzská Polynésie","French Polynesia","French Polynesia");
Insert into CISEL_ZEMI values ("260","TF","ATF","Francouzská jižní území","Francouzská jižní území","French Southern Territories (the)","French Southern Territories (the)");
Insert into CISEL_ZEMI values ("262","DJ","DJI","Džibutská republika","Džibutsko","the Republic of Djibouti","Djibouti");
Insert into CISEL_ZEMI values ("266","GA","GAB","Gabonská republika","Gabon","the Gabonese Republic","Gabon");
Insert into CISEL_ZEMI values ("268","GE","GEO","Gruzie","Gruzie","Georgia","Georgia");
Insert into CISEL_ZEMI values ("270","GM","GMB","Gambijská republika","Gambie","the Republic of The Gambia","Gambia (The)");
Insert into CISEL_ZEMI values ("275","PS","PSE","Okupované palestinské území","Palestinské území (okupované)","the Occupied Palestinian Territory","Palestinian Territory (the Occupied)");
Insert into CISEL_ZEMI values ("276","DE","DEU","Spolková republika Německo","Německo","the Federal Republic of Germany","Germany");
Insert into CISEL_ZEMI values ("288","GH","GHA","Ghanská republika","Ghana","the Republic of Ghana","Ghana");
Insert into CISEL_ZEMI values ("292","GI","GIB","Gibraltar","Gibraltar","Gibraltar","Gibraltar");
Insert into CISEL_ZEMI values ("296","KI","KIR","Republika Kiribati","Kiribati","the Republic of Kiribati","Kiribati");
Insert into CISEL_ZEMI values ("300","GR","GRC","Řecká republika","Řecko","the Hellenic Republic","Greece");
Insert into CISEL_ZEMI values ("304","GL","GRL","Grónsko","Grónsko","Greenland","Greenland");
Insert into CISEL_ZEMI values ("308","GD","GRD","Grenada","Grenada","Grenada","Grenada");
Insert into CISEL_ZEMI values ("312","GP","GLP","Guadeloupe","Guadeloupe","Guadeloupe","Guadeloupe");
Insert into CISEL_ZEMI values ("316","GU","GUM","Guam","Guam","Guam","Guam");
Insert into CISEL_ZEMI values ("320","GT","GTM","Guatemalská republika","Guatemala","the Republic of Guatemala","Guatemala");
Insert into CISEL_ZEMI values ("324","GN","GIN","Guinejská republika","Guinea","the Republic of Guinea","Guinea");
Insert into CISEL_ZEMI values ("328","GY","GUY","Guyanská republika","Guyana","the Republic of Guyana","Guyana");
Insert into CISEL_ZEMI values ("332","HT","HTI","Republika Haiti","Haiti","the Republic of Haiti","Haiti");
Insert into CISEL_ZEMI values ("334","HM","HMD","Heardův ostrov a McDonaldovy ostrovy","Heardův ostrov a McDonaldovy ostrovy","Heard Island and McDonald Islands","Heard Island and McDonald Islands");
Insert into CISEL_ZEMI values ("336","VA","VAT","Svatý stolec (Vatikánský městský stát)","Svatý stolec (Vatikánský městský stát)","Holy See (the) (Vatican City State)","Holy See (the) (Vatican City State)");
Insert into CISEL_ZEMI values ("340","HN","HND","Honduraská republika","Honduras","the Republic of Honduras","Honduras");
Insert into CISEL_ZEMI values ("344","HK","HKG","Zvláštní administrativní oblast Číny Hongkong","Hongkong","the Hong Kong Special Administrative Region of China","Hong Kong");
Insert into CISEL_ZEMI values ("348","HU","HUN","Maďarská republika","Maďarsko","the Republic of Hungary","Hungary");
Insert into CISEL_ZEMI values ("352","IS","ISL","Islandská republika","Island","the Republic of Iceland","Iceland");
Insert into CISEL_ZEMI values ("356","IN","IND","Indická republika","Indie","the Republic of India","India");
Insert into CISEL_ZEMI values ("360","ID","IDN","Indonéská republika","Indonésie","the Republic of Indonesia","Indonesia");
Insert into CISEL_ZEMI values ("364","IR","IRN","Íránská islámská republika","Írán (islámská republika)","the Islamic Republic of Iran","Iran (the Islamic Republic of)");
Insert into CISEL_ZEMI values ("368","IQ","IRQ","Irácká republika","Irák","the Republic of Iraq","Iraq");
Insert into CISEL_ZEMI values ("372","IE","IRL","Irsko","Irsko","Ireland","Ireland");
Insert into CISEL_ZEMI values ("376","IL","ISR","Stát Izrael","Izrael","the State of Israel","Israel");
Insert into CISEL_ZEMI values ("380","IT","ITA","Italská republika","Itálie","the Republic of Italy","Italy");
Insert into CISEL_ZEMI values ("384","CI","CIV","Republika Pobřeží Slonoviny","Pobřeží Slonoviny","the Republic of Côte d'Ivoire","Côte d'Ivoire");
Insert into CISEL_ZEMI values ("388","JM","JAM","Jamajka","Jamajka","Jamaica","Jamaica");
Insert into CISEL_ZEMI values ("392","JP","JPN","Japonsko","Japonsko","Japan","Japan");
Insert into CISEL_ZEMI values ("398","KZ","KAZ","Republika Kazachstán","Kazachstán","the Republic of Kazakhstan","Kazakhstan");
Insert into CISEL_ZEMI values ("400","JO","JOR","Jordánské hášimovské království","Jordánsko","the Hashemite Kingdom of Jordan","Jordan");
Insert into CISEL_ZEMI values ("404","KE","KEN","Keňská republika","Keňa","the Republic of Kenya","Kenya");
Insert into CISEL_ZEMI values ("408","KP","PRK","Korejská lidově demokratická republika","Korea, lidově demokratická republika","the Democratic People's Republic of Korea","Korea (the Democratic People's Republic of)");
Insert into CISEL_ZEMI values ("410","KR","KOR","Korejská republika","Korejská republika","the Republic of Korea","Korea (the Republic of)");
Insert into CISEL_ZEMI values ("414","KW","KWT","Stát Kuvajt","Kuvajt","the State of Kuwait","Kuwait");
Insert into CISEL_ZEMI values ("417","KG","KGZ","Kyrgyzská republika","Kyrgyzstán","the Kyrgyz Republic","Kyrgyzstan");
Insert into CISEL_ZEMI values ("418","LA","LAO","Laoská lidově demokratická republika","Laoská lidově demokratická republika","the Lao People's Democratic Republic","Lao People's Democratic Republic (the)");
Insert into CISEL_ZEMI values ("422","LB","LBN","Libanonská republika","Libanon","the Lebanese Republic","Lebanon");
Insert into CISEL_ZEMI values ("426","LS","LSO","Království Lesotho","Lesotho","the Kingdom of Lesotho","Lesotho");
Insert into CISEL_ZEMI values ("428","LV","LVA","Lotyšská republika","Lotyšsko","the Republic of Latvia","Latvia");
Insert into CISEL_ZEMI values ("430","LR","LBR","Liberijská republika","Libérie","the Republic of Liberia","Liberia");
Insert into CISEL_ZEMI values ("434","LY","LBY","Velká libyjská arabská lidová socialistická džamáhírije","Libyjská arabská džamáhírije","the Socialist People's Libyan Arab Jamahiriya","Libyan Arab Jamahiriya (the)");
Insert into CISEL_ZEMI values ("438","LI","LIE","Lichtenštejnské knížectví","Lichtenštejnsko","the Principality of Liechtenstein","Liechtenstein");
Insert into CISEL_ZEMI values ("440","LT","LTU","Litevská republika","Litva","the Republic of Lithuania","Lithuania");
Insert into CISEL_ZEMI values ("442","LU","LUX","Lucemburské velkovévodství","Lucembursko","the Grand Duchy of Luxembourg","Luxembourg");
Insert into CISEL_ZEMI values ("446","MO","MAC","Zvláštní administrativní oblast Číny Macao","Macao","Macao Special Administrative Region of China","Macao");
Insert into CISEL_ZEMI values ("450","MG","MDG","Madagaskarská republika","Madagaskar","the Republic of Madagascar","Madagascar");
Insert into CISEL_ZEMI values ("454","MW","MWI","Republika Malawi","Malawi","the Republic of Malawi","Malawi");
Insert into CISEL_ZEMI values ("458","MY","MYS","Malajsie","Malajsie","Malaysia","Malaysia");
Insert into CISEL_ZEMI values ("462","MV","MDV","Maledivská republika","Maledivy","the Republic of Maldives","Maldives");
Insert into CISEL_ZEMI values ("466","ML","MLI","Republika Mali","Mali","the Republic of Mali","Mali");
Insert into CISEL_ZEMI values ("470","MT","MLT","Maltská republika","Malta","the Republic of Malta","Malta");
Insert into CISEL_ZEMI values ("474","MQ","MTQ","Martinik","Martinik","Martinique","Martinique");
Insert into CISEL_ZEMI values ("478","MR","MRT","Mauritánská islámská republika","Mauritánie","the Islamic Republic of Mauritania","Mauritania");
Insert into CISEL_ZEMI values ("480","MU","MUS","Mauricijská republika","Mauricius","the Republic of Mauritius","Mauritius");
Insert into CISEL_ZEMI values ("484","MX","MEX","Spojené státy mexické","Mexiko","the United Mexican States","Mexico");
Insert into CISEL_ZEMI values ("492","MC","MCO","Monacké knížectví","Monako","the Principality of Monaco","Monaco");
Insert into CISEL_ZEMI values ("496","MN","MNG","Mongolsko","Mongolsko","Mongolia","Mongolia");
Insert into CISEL_ZEMI values ("498","MD","MDA","Moldavská republika","Moldavsko","the Republic of Moldova","Moldova (the Republic of)");
Insert into CISEL_ZEMI values ("499","ME","MNE","Černá Hora","Černá Hora","Montenegro","Montenegro");
Insert into CISEL_ZEMI values ("500","MS","MSR","Montserrat","Montserrat","Montserrat","Montserrat");
Insert into CISEL_ZEMI values ("504","MA","MAR","Marocké království","Maroko","the Kingdom of Morocco","Morocco");
Insert into CISEL_ZEMI values ("508","MZ","MOZ","Mosambická republika","Mosambik","the Republic of Mozambique","Mozambique");
Insert into CISEL_ZEMI values ("512","OM","OMN","Sultanát Omán","Omán","the Sultanate of Oman","Oman");
Insert into CISEL_ZEMI values ("516","NA","NAM","Namibijská republika","Namibie","the Republic of Namibia","Namibia");
Insert into CISEL_ZEMI values ("520","NR","NRU","Republika Nauru","Nauru","the Republic of Nauru","Nauru");
Insert into CISEL_ZEMI values ("524","NP","NPL","Nepálská federativní demokratická republika","Nepál","the Federal Democratic Republic of Nepal","Nepal");
Insert into CISEL_ZEMI values ("528","NL","NLD","Nizozemské království","Nizozemsko","the Kingdom of the Netherlands","Netherlands (the)");
Insert into CISEL_ZEMI values ("531","CW","CUW","Curaçao","Curaçao","Curaçao","Curaçao");
Insert into CISEL_ZEMI values ("533","AW","ABW","Aruba","Aruba","Aruba","Aruba");
Insert into CISEL_ZEMI values ("534","SX","SXM","Svatý Martin (nizozemská část)","Svatý Martin (nizozemská část)","Sint Maarten (Dutch part)","Sint Maarten (Dutch part)");
Insert into CISEL_ZEMI values ("535","BQ","BES","Bonaire, Svatý Eustach a Saba","Bonaire, Svatý Eustach a Saba","Bonaire, Sint Eustatius and Saba","Bonaire, Sint Eustatius and Saba");
Insert into CISEL_ZEMI values ("540","NC","NCL","Nová Kaledonie","Nová Kaledonie","New Caledonia","New Caledonia");
Insert into CISEL_ZEMI values ("548","VU","VUT","Republika Vanuatu","Vanuatu","the Republic of Vanuatu","Vanuatu");
Insert into CISEL_ZEMI values ("554","NZ","NZL","Nový Zéland","Nový Zéland","New Zealand","New Zealand");
Insert into CISEL_ZEMI values ("558","NI","NIC","Nikaragujská republika","Nikaragua","the Republic of Nicaragua","Nicaragua");
Insert into CISEL_ZEMI values ("562","NE","NER","Nigerská republika","Niger","the Republic of the Niger","Niger (the)");
Insert into CISEL_ZEMI values ("566","NG","NGA","Nigerijská federativní republika","Nigérie","the Federal Republic of Nigeria","Nigeria");
Insert into CISEL_ZEMI values ("570","NU","NIU","Niue","Niue","Niue","Niue");
Insert into CISEL_ZEMI values ("574","NF","NFK","Ostrov Norfolk","Ostrov Norfolk","Norfolk Island","Norfolk Island");
Insert into CISEL_ZEMI values ("578","NO","NOR","Norské království","Norsko","the Kingdom of Norway","Norway");
Insert into CISEL_ZEMI values ("580","MP","MNP","Společenství Ostrovy Severní Mariany","Ostrovy Severní Mariany","the Commonwealth of the Northern Mariana Islands","Northern Mariana Islands (the)");
Insert into CISEL_ZEMI values ("581","UM","UMI","Menší odlehlé ostrovy USA","Menší odlehlé ostrovy USA","United States Minor Outlying Islands (the)","United States Minor Outlying Islands (the)");
Insert into CISEL_ZEMI values ("583","FM","FSM","Federativní státy Mikronésie","Mikronésie, federativní státy","the Federated States of Micronesia","Micronesia (the Federated States of)");
Insert into CISEL_ZEMI values ("584","MH","MHL","Republika Marshallovy ostrovy","Marshallovy ostrovy","the Republic of the Marshall Islands","Marshall Islands (the)");
Insert into CISEL_ZEMI values ("585","PW","PLW","Republika Palau","Palau","the Republic of Palau","Palau");
Insert into CISEL_ZEMI values ("586","PK","PAK","Pákistánská islámská republika","Pákistán","the Islamic Republic of Pakistan","Pakistan");
Insert into CISEL_ZEMI values ("591","PA","PAN","Panamská republika","Panama","the Republic of Panama","Panama");
Insert into CISEL_ZEMI values ("598","PG","PNG","Papua Nová Guinea","Papua Nová Guinea","Papua New Guinea","Papua New Guinea");
Insert into CISEL_ZEMI values ("600","PY","PRY","Paraguayská republika","Paraguay","the Republic of Paraguay","Paraguay");
Insert into CISEL_ZEMI values ("604","PE","PER","Peruánská republika","Peru","the Republic of Peru","Peru");
Insert into CISEL_ZEMI values ("608","PH","PHL","Filipínská republika","Filipíny","the Republic of the Philippines","Philippines (the)");
Insert into CISEL_ZEMI values ("612","PN","PCN","Pitcairn","Pitcairn","Pitcairn","Pitcairn");
Insert into CISEL_ZEMI values ("616","PL","POL","Polská republika","Polsko","the Republic of Poland","Poland");
Insert into CISEL_ZEMI values ("620","PT","PRT","Portugalská republika","Portugalsko","the Portuguese Republic","Portugal");
Insert into CISEL_ZEMI values ("624","GW","GNB","Republika Guinea-Bissau","Guinea-Bissau","the Republic of Guinea-Bissau","Guinea-Bissau");
Insert into CISEL_ZEMI values ("626","TL","TLS","Demokratická republika Východní Timor","Východní Timor","the Democratic Republic of Timor-Leste","Timor-Leste");
Insert into CISEL_ZEMI values ("630","PR","PRI","Portoriko","Portoriko","Puerto Rico","Puerto Rico");
Insert into CISEL_ZEMI values ("634","QA","QAT","Stát Katar","Katar","the State of Qatar","Qatar");
Insert into CISEL_ZEMI values ("638","RE","REU","Réunion","Réunion","Réunion","Réunion");
Insert into CISEL_ZEMI values ("642","RO","ROU","Rumunsko","Rumunsko","Romania","Romania");
Insert into CISEL_ZEMI values ("643","RU","RUS","Ruská federace","Ruská federace","the Russian Federation","Russian Federation (the)");
Insert into CISEL_ZEMI values ("646","RW","RWA","Republika Rwanda","Rwanda","the Republic of Rwanda","Rwanda");
Insert into CISEL_ZEMI values ("652","BL","BLM","Saint Barthélemy","Saint Barthélemy","Saint Barthélemy","Saint Barthélemy");
Insert into CISEL_ZEMI values ("654","SH","SHN","Svatá Helena, Ascension a Tristan da Cunha","Svatá Helena, Ascension a Tristan da Cunha","Saint Helena, Ascension and Tristan da Cunha","Saint Helena, Ascension and Tristan da Cunha");
Insert into CISEL_ZEMI values ("659","KN","KNA","Svatý Kryštof a Nevis","Svatý Kryštof a Nevis","Saint Kitts and Nevis","Saint Kitts and Nevis");
Insert into CISEL_ZEMI values ("660","AI","AIA","Anguilla","Anguilla","Anguilla","Anguilla");
Insert into CISEL_ZEMI values ("662","LC","LCA","Svatá Lucie","Svatá Lucie","Saint Lucia","Saint Lucia");
Insert into CISEL_ZEMI values ("663","MF","MAF","Svatý Martin (francouzská část)","Svatý Martin (francouzská část)","Saint Martin (French part)","Saint Martin (French part)");
Insert into CISEL_ZEMI values ("666","PM","SPM","Saint Pierre a Miquelon","Saint Pierre a Miquelon","Saint Pierre and Miquelon","Saint Pierre and Miquelon");
Insert into CISEL_ZEMI values ("670","VC","VCT","Svatý Vincenc a Grenadiny","Svatý Vincenc a Grenadiny","Saint Vincent and the Grenadines","Saint Vincent and the Grenadines");
Insert into CISEL_ZEMI values ("674","SM","SMR","Republika San Marino","San Marino","the Republic of San Marino","San Marino");
Insert into CISEL_ZEMI values ("678","ST","STP","Demokratická republika Svatý Tomáš a Princův ostrov","Svatý Tomáš a Princův ostrov","the Democratic Republic of Sao Tome and Principe","Sao Tome and Principe");
Insert into CISEL_ZEMI values ("682","SA","SAU","Království Saúdská Arábie","Saúdská Arábie","the Kingdom of Saudi Arabia","Saudi Arabia");
Insert into CISEL_ZEMI values ("686","SN","SEN","Senegalská republika","Senegal","the Republic of Senegal","Senegal");
Insert into CISEL_ZEMI values ("688","RS","SRB","Republika Srbsko","Srbsko","the Republic of Serbia","Serbia");
Insert into CISEL_ZEMI values ("690","SC","SYC","Seychelská republika","Seychely","the Republic of Seychelles","Seychelles");
Insert into CISEL_ZEMI values ("694","SL","SLE","Republika Sierra Leone","Sierra Leone","the Republic of Sierra Leone","Sierra Leone");
Insert into CISEL_ZEMI values ("702","SG","SGP","Singapurská republika","Singapur","the Republic of Singapore","Singapore");
Insert into CISEL_ZEMI values ("703","SK","SVK","Slovenská republika","Slovensko","the Slovak Republic","Slovakia");
Insert into CISEL_ZEMI values ("704","VN","VNM","Vietnamská socialistická republika","Vietnam","the Socialist Republic of Viet Nam","Viet Nam");
Insert into CISEL_ZEMI values ("705","SI","SVN","Slovinská republika","Slovinsko","the Republic of Slovenia","Slovenia");
Insert into CISEL_ZEMI values ("706","SO","SOM","Somálská republika","Somálsko","the Somali Republic","Somalia");
Insert into CISEL_ZEMI values ("710","ZA","ZAF","Jihoafrická republika","Jižní Afrika","the Republic of South Africa","South Africa");
Insert into CISEL_ZEMI values ("716","ZW","ZWE","Republika Zimbabwe","Zimbabwe","the Republic of Zimbabwe","Zimbabwe");
Insert into CISEL_ZEMI values ("724","ES","ESP","Španělské království","Španělsko","the Kingdom of Spain","Spain");
Insert into CISEL_ZEMI values ("728","SS","SSD","Jihosúdánská republika","Jižní Súdán","the Republic of South Sudan","South Sudan");
Insert into CISEL_ZEMI values ("729","SD","SDN","Súdánská republika","Súdán","the Republic of the Sudan","Sudan (the)");
Insert into CISEL_ZEMI values ("732","EH","ESH","Západní Sahara","Západní Sahara","Western Sahara","Western Sahara");
Insert into CISEL_ZEMI values ("740","SR","SUR","Surinamská republika","Surinam","the Republic of Suriname","Suriname");
Insert into CISEL_ZEMI values ("744","SJ","SJM","Svalbard a Jan Mayen","Svalbard a Jan Mayen","Svalbard and Jan Mayen","Svalbard and Jan Mayen");
Insert into CISEL_ZEMI values ("748","SZ","SWZ","Svazijské království","Svazijsko","the Kingdom of Swaziland","Swaziland");
Insert into CISEL_ZEMI values ("752","SE","SWE","Švédské království","Švédsko","the Kingdom of Sweden","Sweden");
Insert into CISEL_ZEMI values ("756","CH","CHE","Švýcarská konfederace","Švýcarsko","the Swiss Confederation","Switzerland");
Insert into CISEL_ZEMI values ("760","SY","SYR","Syrská arabská republika","Syrská arabská republika","the Syrian Arab Republic","Syrian Arab Republic (the)");
Insert into CISEL_ZEMI values ("762","TJ","TJK","Republika Tádžikistán","Tádžikistán","the Republic of Tajikistan","Tajikistan");
Insert into CISEL_ZEMI values ("764","TH","THA","Thajské království","Thajsko","the Kingdom of Thailand","Thailand");
Insert into CISEL_ZEMI values ("768","TG","TGO","Republika Togo","Togo","the Togolese Republic","Togo");
Insert into CISEL_ZEMI values ("772","TK","TKL","Tokelau","Tokelau","Tokelau","Tokelau");
Insert into CISEL_ZEMI values ("776","TO","TON","Království Tonga","Tonga","the Kingdom of Tonga","Tonga");
Insert into CISEL_ZEMI values ("780","TT","TTO","Republika Trinidad a Tobago","Trinidad a Tobago","the Republic of Trinidad and Tobago","Trinidad and Tobago");
Insert into CISEL_ZEMI values ("784","AE","ARE","Spojené arabské emiráty","Spojené arabské emiráty","the United Arab Emirates","United Arab Emirates (the)");
Insert into CISEL_ZEMI values ("788","TN","TUN","Tuniská republika","Tunisko","the Republic of Tunisia","Tunisia");
Insert into CISEL_ZEMI values ("792","TR","TUR","Turecká republika","Turecko","the Republic of Turkey","Turkey");
Insert into CISEL_ZEMI values ("795","TM","TKM","Turkmenistán","Turkmenistán","Turkmenistan","Turkmenistan");
Insert into CISEL_ZEMI values ("796","TC","TCA","Ostrovy Turks a Caicos","Ostrovy Turks a Caicos","Turks and Caicos Islands (the)","Turks and Caicos Islands (the)");
Insert into CISEL_ZEMI values ("798","TV","TUV","Tuvalu","Tuvalu","Tuvalu","Tuvalu");
Insert into CISEL_ZEMI values ("800","UG","UGA","Ugandská republika","Uganda","the Republic of Uganda","Uganda");
Insert into CISEL_ZEMI values ("804","UA","UKR","Ukrajina","Ukrajina","Ukraine","Ukraine");
Insert into CISEL_ZEMI values ("807","MK","MKD","Bývalá jugoslávská republika Makedonie","Makedonie, bývalá jugoslávská republika","the former Yugoslav Republic of Macedonia","Macedonia (the former Yugoslav Republic of)");
Insert into CISEL_ZEMI values ("818","EG","EGY","Egyptská arabská republika","Egypt","the Arab Republic of Egypt","Egypt");
Insert into CISEL_ZEMI values ("826","GB","GBR","Spojené království Velké Británie a Severního Irska","Spojené království","the United Kingdom of Great Britain and Northern Ireland","United Kingdom (the)");
Insert into CISEL_ZEMI values ("831","GG","GGY","Guernsey","Guernsey","Guernsey","Guernsey");
Insert into CISEL_ZEMI values ("832","JE","JEY","Jersey","Jersey","Jersey","Jersey");
Insert into CISEL_ZEMI values ("833","IM","IMN","Ostrov Man","Ostrov Man","Isle of Man","Isle of Man");
Insert into CISEL_ZEMI values ("834","TZ","TZA","Tanzanská sjednocená republika","Tanzanská sjednocená republika","the United Republic of Tanzania","Tanzania, United Republic of");
Insert into CISEL_ZEMI values ("840","US","USA","Spojené státy americké","Spojené státy","the United States of America","United States (the)");
Insert into CISEL_ZEMI values ("850","VI","VIR","Americké Panenské ostrovy","Americké Panenské ostrovy","the Virgin Islands of the United States","Virgin Islands (U.S.)");
Insert into CISEL_ZEMI values ("854","BF","BFA","Burkina Faso","Burkina Faso","Burkina Faso","Burkina Faso");
Insert into CISEL_ZEMI values ("858","UY","URY","Uruguayská východní republika","Uruguay","the Eastern Republic of Uruguay","Uruguay");
Insert into CISEL_ZEMI values ("860","UZ","UZB","Republika Uzbekistán","Uzbekistán","the Republic of Uzbekistan","Uzbekistan");
Insert into CISEL_ZEMI values ("862","VE","VEN","Bolívarovská republika Venezuela","Venezuela","the Bolivarian Republic of Venezuela","Venezuela, Bolivarian Republic of");
Insert into CISEL_ZEMI values ("876","WF","WLF","Ostrovy Wallis a Futuna","Wallis a Futuna","Wallis and Futuna Islands","Wallis and Futuna");
Insert into CISEL_ZEMI values ("882","WS","WSM","Nezávislý stát Samoa","Samoa","the Independent State of Samoa","Samoa");
Insert into CISEL_ZEMI values ("887","YE","YEM","Jemenská republika","Jemen","the Republic of Yemen","Yemen");
Insert into CISEL_ZEMI values ("894","ZM","ZMB","Zambijská republika","Zambie","the Republic of Zambia","Zambia");
