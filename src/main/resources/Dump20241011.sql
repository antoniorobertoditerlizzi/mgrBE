CREATE DATABASE  IF NOT EXISTS `prggestionerichieste` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `prggestionerichieste`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: prggestionerichieste
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_blacklisted_tokens`
--

DROP TABLE IF EXISTS `tbl_blacklisted_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_blacklisted_tokens` (
  `id_token` int unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(250) NOT NULL,
  PRIMARY KEY (`id_token`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_blacklisted_tokens`
--

LOCK TABLES `tbl_blacklisted_tokens` WRITE;
/*!40000 ALTER TABLE `tbl_blacklisted_tokens` DISABLE KEYS */;
INSERT INTO `tbl_blacklisted_tokens` VALUES (42,'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW1vbmUubGVjY2EiLCJpYXQiOjE3Mjg2MzA3NzEsImV4cCI6MTcyODYzMTY3MX0.CvLMhQMXEZ4SpJ4uXovPg9PKDHlTdttfdZtYW0IRgdAYF2e4k66ixW1k2K-QbDcN5Hh5bPwufGKps0ojBWNOcg'),(43,'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW1vbmUubGVjY2EiLCJpYXQiOjE3Mjg2MzA5NzAsImV4cCI6MTcyODYzMTg3MH0.5ZUhbs53e4BCEPXWxXkrAa8X5lP5NC0iY6m9UwYNWfDfmY8rSB-veWJEiqUjRBMln4FlcyxBNhclUZDtDnWipA'),(44,'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvbmlvcm9iZXJ0by5kaXRlcmxpenppIiwiaWF0IjoxNzI4NjMxMDA0LCJleHAiOjE3Mjg2MzE5MDR9.nka3z9f8ZdaGj2bu94yoHh-frMPqI93JeeY4L_-o6G2v7KPkbSmcSG-p96S1fB56Pq_DeSkMnssEVkU5d_crjQ'),(45,'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaW1vbmUubGVjY2EiLCJpYXQiOjE3Mjg2MzEwNzQsImV4cCI6MTcyODYzMTk3NH0.f9z2nsUj0ibwopkPKrWlubnQlT4S4R8yEDlss9gDbO3R-34gIAy9fQOf9AEBzSGnE5HAcCm_1o79jfhywk3s1Q');
/*!40000 ALTER TABLE `tbl_blacklisted_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_flussi_richieste`
--

DROP TABLE IF EXISTS `tbl_flussi_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_flussi_richieste` (
  `id_stato_richiesta_attuale` int unsigned NOT NULL,
  `id_stato_richiesta_successivo` int unsigned NOT NULL,
  `id_tipologia_richiesta` smallint unsigned NOT NULL,
  `id_flusso_richiesta` bigint NOT NULL,
  PRIMARY KEY (`id_stato_richiesta_attuale`,`id_stato_richiesta_successivo`,`id_tipologia_richiesta`),
  KEY `fk_tbl_flussi_ricchieste_tbl_stati_richieste2_idx` (`id_stato_richiesta_successivo`),
  KEY `fk_tbl_flussi_ricchieste_tbl_tipologie_richieste1_idx` (`id_tipologia_richiesta`),
  CONSTRAINT `fk_tbl_flussi_ricchieste_tbl_stati_richieste1` FOREIGN KEY (`id_stato_richiesta_attuale`) REFERENCES `tbl_stati_richieste` (`id_stato_richiesta`),
  CONSTRAINT `fk_tbl_flussi_ricchieste_tbl_stati_richieste2` FOREIGN KEY (`id_stato_richiesta_successivo`) REFERENCES `tbl_stati_richieste` (`id_stato_richiesta`),
  CONSTRAINT `fk_tbl_flussi_ricchieste_tbl_tipologie_richieste1` FOREIGN KEY (`id_tipologia_richiesta`) REFERENCES `tbl_tipologie_richieste` (`id_tipologia_richiesta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_flussi_richieste`
--

LOCK TABLES `tbl_flussi_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_flussi_richieste` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_flussi_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_funzionalita`
--

DROP TABLE IF EXISTS `tbl_funzionalita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_funzionalita` (
  `id_funzionalita` int unsigned NOT NULL AUTO_INCREMENT,
  `codice_funzionalita` varchar(10) NOT NULL,
  `descrizione_funzionalita` varchar(120) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `url` varchar(250) NOT NULL,
  PRIMARY KEY (`id_funzionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_funzionalita`
--

LOCK TABLES `tbl_funzionalita` WRITE;
/*!40000 ALTER TABLE `tbl_funzionalita` DISABLE KEYS */;
INSERT INTO `tbl_funzionalita` VALUES (1,'FU001','Visualizza Dashboard','Dashboard','/dashboard'),(2,'FU002','Visualizza Elenco Richieste','Elenco Richieste','/elencorichieste'),(3,'FU003','Inserisci Richiesta','Salva Richiesta','/inserimentoRichiesta'),(4,'FU004','Referente Ufficio','Referente Ufficio','/referenteUfficio'),(5,'FU005','Responsabile Settore','Responsabile Settore','/responsabileSettore'),(6,'FU006','Comandante','Comandante','/comandante'),(7,'FU007','Ufficio Evasione','Ufficio Evasione','/ufficioEvasione'),(8,'FU008','Evasa','Evasa','/Evasa'),(9,'FU009','Avanti','BTN - Avanti','/avanti'),(10,'FU010','Indietro','BTN - Indietro','/indietro');
/*!40000 ALTER TABLE `tbl_funzionalita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_menu`
--

DROP TABLE IF EXISTS `tbl_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_menu` (
  `id_menu` int unsigned NOT NULL AUTO_INCREMENT,
  `id_funzionalita` int unsigned NOT NULL,
  `id_menu_padre` int unsigned DEFAULT NULL,
  `titolo` varchar(50) DEFAULT NULL,
  `default` bit(1) NOT NULL,
  `posizione_menu` varchar(45) NOT NULL,
  PRIMARY KEY (`id_menu`),
  KEY `fk_tbl_menu_tbl_menu1_idx` (`id_menu_padre`),
  KEY `fk_tbl_menu_tbl_funzionalita1_idx` (`id_funzionalita`),
  CONSTRAINT `fk_tbl_menu_tbl_funzionalita1` FOREIGN KEY (`id_funzionalita`) REFERENCES `tbl_funzionalita` (`id_funzionalita`),
  CONSTRAINT `fk_tbl_menu_tbl_menu1` FOREIGN KEY (`id_menu_padre`) REFERENCES `tbl_menu` (`id_menu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_menu`
--

LOCK TABLES `tbl_menu` WRITE;
/*!40000 ALTER TABLE `tbl_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_modelli`
--

DROP TABLE IF EXISTS `tbl_modelli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_modelli` (
  `id_modello` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_modello` varchar(120) NOT NULL,
  `transcodifica_modello` blob NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_modello`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_modelli`
--

LOCK TABLES `tbl_modelli` WRITE;
/*!40000 ALTER TABLE `tbl_modelli` DISABLE KEYS */;
INSERT INTO `tbl_modelli` VALUES (0,'ALL',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary ''),(1,'Acquisto',_binary '{\r\n    \"Destinatario\": {\r\n        \"NomeDestinatario\": \"\",\r\n        \"RecapitiPEC\": [\r\n            \"\"\r\n        ],\r\n        \"RecapitiTelefonico\": [\r\n            \"\"\r\n        ],\r\n        \"Indirizzo\": [\r\n            \"\"\r\n        ]\r\n    },\r\n    \"TipologiaModello\": \"con tabella o con corpo del testo\",\r\n    \"Note\": \"\",\r\n    \"Oggetto\": \"Acquisto\",\r\n    \"Sezioni\": [\r\n        {\r\n            \"DescrizioneSezione\": \"\",\r\n            \"TipologiaSezione\": \"Dipartimento|Direzione|Comando\"\r\n        }\r\n    ],\r\n    \"SottoSezioneComando\": {\r\n        \"DescrizioneSottoSezione\": \"\",\r\n        \"ElencoCampi\": []\r\n    },\r\n    \"Firma\": {\r\n        \"Firmatario\": [\r\n            \"\"\r\n        ]\r\n    }\r\n}',_binary ''),(2,'Assegnazione Materiale Informatico',_binary '{\r\n    \"Destinatario\": {\r\n        \"NomeDestinatario\": \"\",\r\n        \"RecapitiPEC\": [\r\n            \"\"\r\n        ],\r\n        \"RecapitiTelefonico\": [\r\n            \"\"\r\n        ],\r\n        \"Indirizzo\": [\r\n            \"\"\r\n        ]\r\n    },\r\n    \"TipologiaModello\": \"con tabella o con corpo del testo\",\r\n    \"Note\": \"\",\r\n    \"Oggetto\": \"Assegnazione Materiale Informatico\",\r\n    \"Sezioni\": [\r\n        {\r\n            \"DescrizioneSezione\": \"\",\r\n            \"TipologiaSezione\": \"Dipartimento|Direzione|Comando\"\r\n        }\r\n    ],\r\n    \"SottoSezioneComando\": {\r\n        \"DescrizioneSottoSezione\": \"\",\r\n        \"ElencoCampi\": []\r\n    },\r\n    \"Firma\": {\r\n        \"Firmatario\": [\r\n            \"\"\r\n        ]\r\n    }\r\n}',_binary ''),(3,'Assegnazione Materiale Operativo',_binary '{\r\n    \"Destinatario\": {\r\n        \"NomeDestinatario\": \"\",\r\n        \"RecapitiPEC\": [\r\n            \"\"\r\n        ],\r\n        \"RecapitiTelefonico\": [\r\n            \"\"\r\n        ],\r\n        \"Indirizzo\": [\r\n            \"\"\r\n        ]\r\n    },\r\n    \"TipologiaModello\": \"con tabella o con corpo del testo\",\r\n    \"Note\": \"\",\r\n    \"Oggetto\": \"Assegnazione Materiale Operativo\",\r\n    \"Sezioni\": [\r\n        {\r\n            \"DescrizioneSezione\": \"\",\r\n            \"TipologiaSezione\": \"Dipartimento|Direzione|Comando\"\r\n        }\r\n    ],\r\n    \"SottoSezioneComando\": {\r\n        \"DescrizioneSottoSezione\": \"\",\r\n        \"ElencoCampi\": []\r\n    },\r\n    \"Firma\": {\r\n        \"Firmatario\": [\r\n            \"\"\r\n        ]\r\n    }\r\n}',_binary ''),(4,'Assegnazione Materiale Logistico',_binary '{\r\n    \"Destinatario\": {\r\n        \"NomeDestinatario\": \"\",\r\n        \"RecapitiPEC\": [\r\n            \"\"\r\n        ],\r\n        \"RecapitiTelefonico\": [\r\n            \"\"\r\n        ],\r\n        \"Indirizzo\": [\r\n            \"\"\r\n        ]\r\n    },\r\n    \"TipologiaModello\": \"con tabella o con corpo del testo\",\r\n    \"Note\": \"\",\r\n    \"Oggetto\": \"Assegnazione Materiale Logistico\",\r\n    \"Sezioni\": [\r\n        {\r\n            \"DescrizioneSezione\": \"\",\r\n            \"TipologiaSezione\": \"Dipartimento|Direzione|Comando\"\r\n        }\r\n    ],\r\n    \"SottoSezioneComando\": {\r\n        \"DescrizioneSottoSezione\": \"\",\r\n        \"ElencoCampi\": []\r\n    },\r\n    \"Firma\": {\r\n        \"Firmatario\": [\r\n            \"\"\r\n        ]\r\n    }\r\n}',_binary ''),(5,'Corsi',_binary '{\r\n    \"Destinatario\": {\r\n        \"NomeDestinatario\": \"\",\r\n        \"RecapitiPEC\": [\r\n            \"\"\r\n        ],\r\n        \"RecapitiTelefonico\": [\r\n            \"\"\r\n        ],\r\n        \"Indirizzo\": [\r\n            \"\"\r\n        ]\r\n    },\r\n    \"TipologiaModello\": \"con tabella o con corpo del testo\",\r\n    \"Note\": \"\",\r\n    \"Oggetto\": \"Corsi\",\r\n    \"Sezioni\": [\r\n        {\r\n            \"DescrizioneSezione\": \"\",\r\n            \"TipologiaSezione\": \"Dipartimento|Direzione|Comando\"\r\n        }\r\n    ],\r\n    \"SottoSezioneComando\": {\r\n        \"DescrizioneSottoSezione\": \"\",\r\n        \"ElencoCampi\": []\r\n    },\r\n    \"Firma\": {\r\n        \"Firmatario\": [\r\n            \"\"\r\n        ]\r\n    }\r\n}',_binary ''),(6,'DPI',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"grouped_product_type\",\r\n            \"label\": \"Campi raggruppati\",\r\n            \"type\": \"grouped\",\r\n            \"required\": true,\r\n            \"multiple\": true,\r\n            \"value\": [\r\n                {\r\n                    \"name\": \"product_type\",\r\n                    \"label\": \"Prodotto\",\r\n                    \"type\": \"text\",\r\n                    \"required\": true,\r\n                    \"regex\": \"\",\r\n                    \"value\": \"\"\r\n                },\r\n                {\r\n                    \"name\": \"quantity_type\",\r\n                    \"label\": \"Quantità\",\r\n                    \"type\": \"number\",\r\n                    \"required\": true,\r\n                    \"regex\": \"\",\r\n                    \"value\": \"\"\r\n                },\r\n                {\r\n                    \"name\": \"Descrizione\",\r\n                    \"type\": \"textarea\",\r\n                    \"required\": true,\r\n                    \"regex\": \"\",\r\n                    \"value\": \"\"\r\n                },\r\n                {\r\n                    \"name\": \"Campo select ?\",\r\n                    \"type\": \"select\",\r\n                    \"subitem\": [\r\n                        {\r\n                            \"name\": \"type1\",\r\n                            \"value\": \"Tipologia 1\"\r\n                        },\r\n                        {\r\n                            \"name\": \"type2\",\r\n                            \"value\": \"Tipologia 2\"\r\n                        },\r\n                        {\r\n                            \"name\": \"type3\",\r\n                            \"value\": \"Tipologia 3\"\r\n                        }\r\n                    ]\r\n                }\r\n            ]\r\n        },\r\n        {\r\n            \"name\": \"Note\",\r\n            \"type\": \"textarea\",\r\n            \"multiple\": true,\r\n            \"value\": \"\"\r\n        },\r\n        {\r\n            \"name\": \"Tipologia\",\r\n            \"type\": \"select\",\r\n            \"subitem\": [\r\n                {\r\n                    \"name\": \"type1\",\r\n                    \"value\": \"Tipologia 1\"\r\n                },\r\n                {\r\n                    \"name\": \"type2\",\r\n                    \"value\": \"Tipologia 2\"\r\n                },\r\n                {\r\n                    \"name\": \"type3\",\r\n                    \"value\": \"Tipologia 3\"\r\n                }\r\n            ],\r\n            \"value\": \"\"\r\n        },\r\n        {\r\n            \"name\": \"Tipologia\",\r\n            \"type\": \"radio\",\r\n            \"subitem\": [\r\n                {\r\n                    \"name\": \"nome1\",\r\n                    \"value\": \"Tipologia 1\"\r\n                },\r\n                {\r\n                    \"name\": \"nam2\",\r\n                    \"value\": \"Tipologia 2\"\r\n                },\r\n                {\r\n                    \"name\": \"type3\",\r\n                    \"value\": \"Tipologia 3\"\r\n                }\r\n            ]\r\n        },\r\n        {\r\n            \"name\": \"Tipologia\",\r\n            \"type\": \"checkbox\",\r\n            \"subitem\": [\r\n                {\r\n                    \"name\": \"nome1\",\r\n                    \"value\": \"Tipologia 1\"\r\n                },\r\n                {\r\n                    \"name\": \"type2\",\r\n                    \"value\": \"Tipologia 2\"\r\n                },\r\n                {\r\n                    \"name\": \"type3\",\r\n                    \"value\": \"Tipologia 3\"\r\n                }\r\n            ]\r\n        },\r\n        {\r\n            \"name\": \"Data\",\r\n            \"type\": \"date\",\r\n            \"value\": \"timestamp\",\r\n            \"required\": true,\r\n            \"regex\": \"\",\r\n            \"multiple\": true\r\n        }\r\n    ]\r\n}',_binary ''),(17,'DAWS',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"nameCIAO\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary ''),(18,'prova200924',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary ''),(19,'prova230924',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary ''),(20,'tipologia2',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary ''),(21,'Riparazione mezzo',_binary '{\r\n    \"data\": [\r\n        {\r\n            \"name\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',_binary '');
/*!40000 ALTER TABLE `tbl_modelli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_modelli_compilati`
--

DROP TABLE IF EXISTS `tbl_modelli_compilati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_modelli_compilati` (
  `id_modello_compilato` int unsigned NOT NULL AUTO_INCREMENT,
  `file_modello` blob NOT NULL,
  `id_modello` int unsigned NOT NULL,
  `id_richiesta` int unsigned NOT NULL,
  `transcodifica_modello_compilato` blob NOT NULL,
  PRIMARY KEY (`id_modello_compilato`),
  KEY `fk_modelli_compilati_tbl_modelli1_idx` (`id_modello`),
  KEY `fk_modelli_compilati_tbl_richieste1_idx` (`id_richiesta`),
  CONSTRAINT `fk_modelli_compilati_tbl_modelli1` FOREIGN KEY (`id_modello`) REFERENCES `tbl_modelli` (`id_modello`),
  CONSTRAINT `fk_modelli_compilati_tbl_richieste1` FOREIGN KEY (`id_richiesta`) REFERENCES `tbl_richieste` (`id_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_modelli_compilati`
--

LOCK TABLES `tbl_modelli_compilati` WRITE;
/*!40000 ALTER TABLE `tbl_modelli_compilati` DISABLE KEYS */;
INSERT INTO `tbl_modelli_compilati` VALUES (5,_binary '{\r\n    \"data\": [\r\n        {\r\n            \"nameCIAO\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}',0,5,_binary '{\r\n    \"data\": [\r\n        {\r\n            \"nameCIAO\": \"forms_all_offices\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getUserOffices\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        },\r\n        {\r\n            \"name\": \"forms_all_priority\",\r\n            \"type\": \"select\",\r\n            \"get_data\": {\r\n                \"api\": \"getPriorities\"\r\n            },\r\n            \"attributes\": {\r\n                \"required\": true,\r\n                \"multiple\": false\r\n            }\r\n        }\r\n    ]\r\n}');
/*!40000 ALTER TABLE `tbl_modelli_compilati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_modelli_tipologie_richieste`
--

DROP TABLE IF EXISTS `tbl_modelli_tipologie_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_modelli_tipologie_richieste` (
  `id_modello_tipologia_richiesta` int NOT NULL AUTO_INCREMENT,
  `id_modello` int unsigned NOT NULL,
  `id_tipologia_richiesta` smallint unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  `data_disattivazione` datetime DEFAULT NULL,
  PRIMARY KEY (`id_modello_tipologia_richiesta`,`id_modello`,`id_tipologia_richiesta`),
  KEY `fk_tbl_modelli_tipologie_richieste_tbl_tipologie_richieste1_idx` (`id_tipologia_richiesta`),
  KEY `fk_tbl_modelli_tipologie_richieste_tbl_modello` (`id_modello`),
  CONSTRAINT `fk_tbl_modelli_tipologie_richieste_tbl_modello` FOREIGN KEY (`id_modello`) REFERENCES `tbl_modelli` (`id_modello`),
  CONSTRAINT `fk_tbl_modelli_tipologie_richieste_tbl_tipologie_richieste` FOREIGN KEY (`id_tipologia_richiesta`) REFERENCES `tbl_tipologie_richieste` (`id_tipologia_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_modelli_tipologie_richieste`
--

LOCK TABLES `tbl_modelli_tipologie_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_modelli_tipologie_richieste` DISABLE KEYS */;
INSERT INTO `tbl_modelli_tipologie_richieste` VALUES (2,0,20,_binary '','2024-12-31 00:00:00'),(10,3,20,_binary '','2024-12-31 00:00:00'),(11,3,1,_binary '','2024-12-31 00:00:00'),(12,3,3,_binary '','2024-12-31 00:00:00'),(13,3,4,_binary '',NULL),(14,3,6,_binary '','2024-12-31 00:00:00'),(15,18,31,_binary '',NULL),(16,19,32,_binary '',NULL),(17,20,33,_binary '',NULL),(18,21,35,_binary '',NULL);
/*!40000 ALTER TABLE `tbl_modelli_tipologie_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_priorita`
--

DROP TABLE IF EXISTS `tbl_priorita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_priorita` (
  `id_priorita` smallint unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_priorita` varchar(120) NOT NULL,
  PRIMARY KEY (`id_priorita`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_priorita`
--

LOCK TABLES `tbl_priorita` WRITE;
/*!40000 ALTER TABLE `tbl_priorita` DISABLE KEYS */;
INSERT INTO `tbl_priorita` VALUES (1,'Alta'),(2,'Media'),(3,'Bassa');
/*!40000 ALTER TABLE `tbl_priorita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_richieste`
--

DROP TABLE IF EXISTS `tbl_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_richieste` (
  `id_richiesta` int unsigned NOT NULL AUTO_INCREMENT,
  `numero_richiesta` char(14) NOT NULL COMMENT 'COMPLETARE IL RAGIONAMENTO\n',
  `id_stato_richiesta` int unsigned NOT NULL COMMENT 'Stato attuale della richiesta',
  `id_tipologia_richiesta` smallint unsigned NOT NULL,
  `richiesta_personale` bit(1) NOT NULL DEFAULT b'0',
  `id_priorita` smallint unsigned NOT NULL,
  `data_inserimento_richiesta` datetime NOT NULL,
  `data_ultimo_stato_richiesta` datetime NOT NULL,
  `id_utente_ufficio_ruolo_stato_corrente` int unsigned NOT NULL COMMENT 'utente che ha palesato lo stato attuale della richiesta',
  `id_utente_ufficio_ruolo_stato_iniziale` int unsigned NOT NULL COMMENT 'Utente con relativo ufficio e ruolo all''atto della richiesta',
  `id_settore_ufficio` smallint unsigned NOT NULL,
  PRIMARY KEY (`id_richiesta`),
  KEY `fk_tbl_richieste_tbl_utenti_uffici_ruoli1_idx` (`id_utente_ufficio_ruolo_stato_iniziale`),
  KEY `fk_tbl_richieste_tbl_tipologie_richieste1_idx` (`id_tipologia_richiesta`),
  KEY `fk_tbl_richieste_tbl_priorita1_idx` (`id_priorita`),
  KEY `fk_tbl_richieste_tbl_stati_richieste1_idx` (`id_stato_richiesta`),
  KEY `fk_tbl_richieste_tbl_utenti_uffici_ruoli_statocorrente1_idx` (`id_utente_ufficio_ruolo_stato_corrente`),
  KEY `fk_tbl_richieste_tbl_settori_uffici1_idx` (`id_settore_ufficio`),
  CONSTRAINT `fk_tbl_richieste_tbl_priorita` FOREIGN KEY (`id_priorita`) REFERENCES `tbl_priorita` (`id_priorita`),
  CONSTRAINT `fk_tbl_richieste_tbl_settori_uffici` FOREIGN KEY (`id_settore_ufficio`) REFERENCES `tbl_settori_uffici` (`id_settore_ufficio`),
  CONSTRAINT `fk_tbl_richieste_tbl_stati_richieste` FOREIGN KEY (`id_stato_richiesta`) REFERENCES `tbl_stati_richieste` (`id_stato_richiesta`),
  CONSTRAINT `fk_tbl_richieste_tbl_tipologie_richieste` FOREIGN KEY (`id_tipologia_richiesta`) REFERENCES `tbl_tipologie_richieste` (`id_tipologia_richiesta`),
  CONSTRAINT `fk_tbl_richieste_tbl_utenti_uffici_ruoli_stato_corrente` FOREIGN KEY (`id_utente_ufficio_ruolo_stato_corrente`) REFERENCES `tbl_utenti_uffici_ruoli` (`id_utente_ufficio_ruolo`),
  CONSTRAINT `fk_tbl_richieste_tbl_utenti_uffici_ruoli_stato_iniziale` FOREIGN KEY (`id_utente_ufficio_ruolo_stato_iniziale`) REFERENCES `tbl_utenti_uffici_ruoli` (`id_utente_ufficio_ruolo`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_richieste`
--

LOCK TABLES `tbl_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_richieste` DISABLE KEYS */;
INSERT INTO `tbl_richieste` VALUES (1,'123123',1,1,_binary '',1,'2024-07-22 00:00:00','2024-07-24 08:14:27',2,1,2),(5,'55555555',2,1,_binary '',1,'2024-07-22 00:00:00','2024-07-22 00:00:00',2,1,1),(6,'123123',8,1,_binary '',1,'2023-07-22 00:00:00','2024-07-24 08:35:40',2,1,5),(23,'123',2,1,_binary '\0',1,'2024-07-22 16:34:47','2024-07-22 16:34:47',2,1,1),(24,'123',12,1,_binary '\0',1,'2024-07-22 16:40:41','2024-07-22 16:40:41',2,1,1),(26,'REQ-Acqu-24072',5,1,_binary '\0',1,'2024-07-22 20:19:14','2024-07-22 20:19:14',2,1,1),(28,'R-A-722-6095-0',2,1,_binary '\0',1,'2024-07-22 20:23:20','2024-07-22 20:23:20',2,1,5),(29,'R-A-2-1043-000',1,1,_binary '\0',1,'2024-07-22 20:24:33','2024-07-22 20:24:33',2,1,1),(30,'R-A-2407-3002-',3,1,_binary '\0',1,'2024-07-22 20:25:19','2024-07-22 20:25:19',3,3,1),(31,'R-A-2407-129-0',4,1,_binary '\0',1,'2024-07-22 20:30:28','2024-07-22 20:30:28',3,3,1),(32,'R-A-2407-525-1',1,1,_binary '\0',1,'2024-07-22 20:35:34','2024-07-22 20:35:34',3,3,1),(33,'R-A-24-934-1Z',5,1,_binary '\0',1,'2024-07-22 20:35:51','2024-07-22 20:35:51',3,3,1),(34,'R-A-24-679-1ZZ',6,1,_binary '\0',1,'2024-07-22 20:36:59','2024-07-22 20:36:59',3,3,1),(47,'R-A-24-111-1ZZ',7,1,_binary '\0',1,'2024-07-23 08:50:25','2024-07-23 08:50:25',1,1,2),(57,'R-A-24-682-1ZZ',8,1,_binary '\0',1,'2024-07-23 16:21:32','2024-07-23 16:21:32',1,1,1),(58,'R-A-24-604-1ZZ',9,1,_binary '\0',1,'2024-07-24 08:36:17','2024-07-24 08:36:17',1,1,1),(59,'RA2407244821ZZ',10,1,_binary '\0',1,'2024-07-24 08:47:51','2024-07-24 08:47:51',1,1,1),(60,'RA2407242031ZZ',11,1,_binary '\0',1,'2024-07-24 09:10:01','2024-07-24 09:10:01',1,1,1),(61,'RA2407241751ZZ',12,1,_binary '\0',1,'2024-07-24 09:47:00','2024-07-24 09:47:00',3,3,3),(62,'RA2407243251ZZ',2,1,_binary '\0',1,'2024-07-24 09:47:34','2024-07-24 09:47:34',1,1,1),(63,'RA2407240781ZZ',7,1,_binary '\0',1,'2024-07-24 09:50:15','2024-07-24 09:50:15',3,3,3),(72,'RA2407247021ZZ',6,1,_binary '\0',1,'2024-07-24 10:01:26','2024-07-24 10:01:26',1,1,1),(73,'RA2407248861ZZ',5,1,_binary '\0',1,'2024-07-24 10:04:55','2024-07-24 10:04:55',1,1,1),(74,'RA2407246341ZZ',1,1,_binary '\0',1,'2024-07-24 10:11:36','2024-07-24 10:11:36',1,1,1),(75,'RA2407246541ZZ',1,1,_binary '\0',1,'2024-07-24 10:28:49','2024-07-24 10:28:49',1,1,1),(76,'RA2407249621ZZ',4,1,_binary '\0',1,'2024-07-24 10:41:13','2024-07-24 10:41:13',1,1,1),(77,'RA2408193141ZZ',1,1,_binary '\0',1,'2024-08-19 13:27:16','2024-08-19 13:27:16',1,1,1),(87,'RA2409097311ZZ',5,1,_binary '',1,'2024-09-09 08:19:38','2024-09-09 08:19:38',1,1,5),(88,'RA2409099121ZZ',2,1,_binary '',2,'2024-09-09 10:01:23','2024-09-09 10:01:23',1,1,5),(89,'RA2409163711ZZ',1,1,_binary '',1,'2024-09-16 12:40:07','2024-09-16 12:40:07',1,1,5),(90,'RA2409198412ZZ',12,1,_binary '\0',1,'2024-09-19 12:17:59','2024-09-19 12:17:59',1,1,1),(91,'RA2409192183ZZ',1,1,_binary '\0',1,'2024-09-19 12:18:30','2024-09-19 12:18:30',1,1,1),(92,'RA2409234722ZZ',1,1,_binary '\0',1,'2024-09-23 08:27:46','2024-09-23 08:27:46',1,1,1),(93,'RA2409238682ZZ',7,20,_binary '\0',1,'2024-09-23 08:28:30','2024-09-23 08:28:30',1,1,1),(94,'RA2409235422ZZ',8,1,_binary '\0',1,'2024-09-23 09:49:25','2024-09-23 09:49:25',1,1,1),(95,'RA2409235313ZZ',1,20,_binary '\0',2,'2024-09-23 09:50:15','2024-09-23 09:50:15',1,1,1),(96,'RD2410025033ZZ',1,6,_binary '\0',1,'2024-10-02 11:12:51','2024-10-02 11:12:51',1,1,1),(97,'RD2410074552ZZ',1,6,_binary '\0',1,'2024-10-07 15:20:47','2024-10-07 15:20:47',1,1,1);
/*!40000 ALTER TABLE `tbl_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_ruoli`
--

DROP TABLE IF EXISTS `tbl_ruoli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_ruoli` (
  `id_ruolo` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_ruolo` varchar(50) NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_ruolo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_ruoli`
--

LOCK TABLES `tbl_ruoli` WRITE;
/*!40000 ALTER TABLE `tbl_ruoli` DISABLE KEYS */;
INSERT INTO `tbl_ruoli` VALUES (1,'Utente',_binary ''),(2,'Referente Ufficio',_binary ''),(3,'Responsabile Settore',_binary ''),(4,'Comandante',_binary '');
/*!40000 ALTER TABLE `tbl_ruoli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_ruoli_funzionalita`
--

DROP TABLE IF EXISTS `tbl_ruoli_funzionalita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_ruoli_funzionalita` (
  `id_ruolo_funzionalita` int unsigned NOT NULL AUTO_INCREMENT,
  `id_ruolo` int unsigned NOT NULL,
  `id_funzionalita` int unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_ruolo_funzionalita`),
  KEY `fk_tbl_ruoli_has_tbl_funzionalita_tbl_funzionalita1_idx` (`id_funzionalita`),
  KEY `fk_tbl_ruoli_has_tbl_funzionalita_tbl_ruoli1_idx` (`id_ruolo`),
  CONSTRAINT `fk_tbl_ruoli_has_tbl_funzionalita_tbl_funzionalita1` FOREIGN KEY (`id_funzionalita`) REFERENCES `tbl_funzionalita` (`id_funzionalita`),
  CONSTRAINT `fk_tbl_ruoli_has_tbl_funzionalita_tbl_ruoli1` FOREIGN KEY (`id_ruolo`) REFERENCES `tbl_ruoli` (`id_ruolo`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_ruoli_funzionalita`
--

LOCK TABLES `tbl_ruoli_funzionalita` WRITE;
/*!40000 ALTER TABLE `tbl_ruoli_funzionalita` DISABLE KEYS */;
INSERT INTO `tbl_ruoli_funzionalita` VALUES (17,4,1,_binary ''),(18,4,4,_binary ''),(19,4,5,_binary ''),(20,4,6,_binary ''),(21,4,7,_binary ''),(22,4,8,_binary ''),(23,1,2,_binary ''),(24,1,3,_binary ''),(25,4,9,_binary ''),(26,4,10,_binary '');
/*!40000 ALTER TABLE `tbl_ruoli_funzionalita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settori`
--

DROP TABLE IF EXISTS `tbl_settori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_settori` (
  `id_settore` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_settore` varchar(120) NOT NULL,
  `email_settore` varchar(50) DEFAULT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_settore`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settori`
--

LOCK TABLES `tbl_settori` WRITE;
/*!40000 ALTER TABLE `tbl_settori` DISABLE KEYS */;
INSERT INTO `tbl_settori` VALUES (1,'Settore Acquisti','acquisti@vigilfuoco.it',_binary ''),(2,'Settore Informatico','informatica@vigilfuoco.it',_binary ''),(3,'Settore Ragioneria','rag@vigilfuoco.it',_binary ''),(4,'Settore Tep','tep@vigilfuoco.it',_binary ''),(5,'Settore TLC','tlc@vigilfuoco.it',_binary '');
/*!40000 ALTER TABLE `tbl_settori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settori_richieste`
--

DROP TABLE IF EXISTS `tbl_settori_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_settori_richieste` (
  `id_settore_richiesta` bigint NOT NULL AUTO_INCREMENT,
  `id_settore` int unsigned NOT NULL,
  `id_tipologia_richiesta` smallint unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_settore_richiesta`,`id_settore`,`id_tipologia_richiesta`),
  KEY `fk_tbl_settore_richiesta_has_tbl_richieste_tbl_settore_rich1_idx` (`id_settore`),
  KEY `fk_tbl_settori_richieste_tbl_tipologie_richieste1_idx` (`id_tipologia_richiesta`),
  CONSTRAINT `fk_tbl_settori_richieste_tbl_settori` FOREIGN KEY (`id_settore`) REFERENCES `tbl_settori` (`id_settore`),
  CONSTRAINT `fk_tbl_settori_richieste_tbl_tipologie_richieste` FOREIGN KEY (`id_tipologia_richiesta`) REFERENCES `tbl_tipologie_richieste` (`id_tipologia_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settori_richieste`
--

LOCK TABLES `tbl_settori_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_settori_richieste` DISABLE KEYS */;
INSERT INTO `tbl_settori_richieste` VALUES (1,1,1,_binary ''),(2,1,1,_binary ''),(3,1,2,_binary '\0');
/*!40000 ALTER TABLE `tbl_settori_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_settori_uffici`
--

DROP TABLE IF EXISTS `tbl_settori_uffici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_settori_uffici` (
  `id_settore_ufficio` smallint unsigned NOT NULL AUTO_INCREMENT,
  `id_settore` int unsigned NOT NULL,
  `id_ufficio` int unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_settore_ufficio`),
  KEY `fk_tbl_settori_uffici_tbl_settori_idx1` (`id_settore`),
  KEY `fk_tbl_settori_uffici_tbl_uffici_idx1` (`id_ufficio`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_tbl_settori_uffici_tbl_settori` FOREIGN KEY (`id_settore`) REFERENCES `tbl_settori` (`id_settore`),
  CONSTRAINT `fk_tbl_settori_uffici_tbl_uffici` FOREIGN KEY (`id_ufficio`) REFERENCES `tbl_uffici` (`id_ufficio`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_settori_uffici`
--

LOCK TABLES `tbl_settori_uffici` WRITE;
/*!40000 ALTER TABLE `tbl_settori_uffici` DISABLE KEYS */;
INSERT INTO `tbl_settori_uffici` VALUES (1,1,1,_binary ''),(2,3,1,_binary ''),(3,2,2,_binary ''),(4,5,2,_binary ''),(5,2,3,_binary ''),(6,2,5,_binary '');
/*!40000 ALTER TABLE `tbl_settori_uffici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_stati_richieste`
--

DROP TABLE IF EXISTS `tbl_stati_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_stati_richieste` (
  `id_stato_richiesta` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_stato_richiesta` varchar(120) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `descrizione_stato` varchar(120) NOT NULL,
  `percentuale` int NOT NULL,
  `colore` varchar(10) NOT NULL,
  `tab_wizard` int NOT NULL,
  PRIMARY KEY (`id_stato_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_stati_richieste`
--

LOCK TABLES `tbl_stati_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_stati_richieste` DISABLE KEYS */;
INSERT INTO `tbl_stati_richieste` VALUES (1,'Inserita',_binary '','',10,'#808080',0),(2,'Inviata a Referente Ufficio',_binary '','',30,'orange',1),(3,'Approvata Referente Ufficio',_binary '','',50,'orange',1),(4,'Richiesta Integrazione Referente Ufficio',_binary '','',50,'orange',1),(5,'Rifiutata Referente Ufficio',_binary '','',100,'orange',5),(6,'Approvata Responsabile Settore',_binary '','',70,'#00BFFF',2),(7,'Richiesta Integrazione Responsabile Settore',_binary '','',70,'#00BFFF',2),(8,'Rifiutata Responsabile Settore',_binary '','',100,'#00BFFF',5),(9,'Approvata Comandante',_binary '','',90,'#e74c3c',3),(10,'Negato Consenso',_binary '','',100,'#e74c3c',5),(11,'Inviata Ufficio Evasione',_binary '','',95,'#d9d70a',4),(12,'Evasa',_binary '','',100,'#4CAF50',5),(998,'Richiesta Privata',_binary '','',0,'#808080',0),(999,'Richiesta Ufficio',_binary '','',0,'#808080',0);
/*!40000 ALTER TABLE `tbl_stati_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_storico_stati_richieste`
--

DROP TABLE IF EXISTS `tbl_storico_stati_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_storico_stati_richieste` (
  `id_storico_stato_richiesta` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_stato_richiesta` datetime NOT NULL,
  `note` varchar(3000) DEFAULT NULL,
  `id_stato_richiesta` int unsigned NOT NULL,
  `id_richiesta` int unsigned NOT NULL,
  `id_utente_ufficio_ruolo` int unsigned NOT NULL,
  PRIMARY KEY (`id_storico_stato_richiesta`),
  KEY `fk_tbl_storico_stati_richieste_tbl_stati_richieste1_idx` (`id_stato_richiesta`),
  KEY `fk_tbl_storico_stati_richieste_tbl_richieste1_idx` (`id_richiesta`),
  KEY `fk_tbl_storico_stati_richieste_tbl_utenti_uffici_ruoli1_idx` (`id_utente_ufficio_ruolo`),
  CONSTRAINT `fk_tbl_storico_stati_richieste_tbl_richieste` FOREIGN KEY (`id_richiesta`) REFERENCES `tbl_richieste` (`id_richiesta`),
  CONSTRAINT `fk_tbl_storico_stati_richieste_tbl_stati_richieste` FOREIGN KEY (`id_stato_richiesta`) REFERENCES `tbl_stati_richieste` (`id_stato_richiesta`),
  CONSTRAINT `fk_tbl_storico_stati_richieste_tbl_utenti_uffici_ruoli1` FOREIGN KEY (`id_utente_ufficio_ruolo`) REFERENCES `tbl_utenti_uffici_ruoli` (`id_utente_ufficio_ruolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_storico_stati_richieste`
--

LOCK TABLES `tbl_storico_stati_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_storico_stati_richieste` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_storico_stati_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tipologie_richieste`
--

DROP TABLE IF EXISTS `tbl_tipologie_richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_tipologie_richieste` (
  `id_tipologia_richiesta` smallint unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_tipologia_richiesta` varchar(120) NOT NULL,
  `id_stato_richiesta_partenza` int unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_tipologia_richiesta`),
  KEY `fk_tbl_tipologie_richieste_tbl_stati_richieste1_idx` (`id_stato_richiesta_partenza`),
  CONSTRAINT `fk_tbl_tipologie_richieste_tbl_stati_richieste` FOREIGN KEY (`id_stato_richiesta_partenza`) REFERENCES `tbl_stati_richieste` (`id_stato_richiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tipologie_richieste`
--

LOCK TABLES `tbl_tipologie_richieste` WRITE;
/*!40000 ALTER TABLE `tbl_tipologie_richieste` DISABLE KEYS */;
INSERT INTO `tbl_tipologie_richieste` VALUES (1,'Acquisto',999,_binary ''),(2,'Assegnazione Materiale Informatico',3,_binary ''),(3,'Assegnazione Materiale Operativo',3,_binary ''),(4,'Assegnazione Materiale Logistico',3,_binary ''),(5,'Corsi',3,_binary ''),(6,'DPI',3,_binary ''),(7,'Fascicolo Sanitario',998,_binary ''),(20,'Automezzo',999,_binary ''),(31,'prova200924',999,_binary ''),(32,'prova230924',999,_binary ''),(33,'tipologia2',998,_binary ''),(35,'Riparazione mezzo',999,_binary '');
/*!40000 ALTER TABLE `tbl_tipologie_richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_uffici`
--

DROP TABLE IF EXISTS `tbl_uffici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_uffici` (
  `id_ufficio` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione_ufficio` varchar(120) NOT NULL,
  `descrizione_comando` varchar(120) NOT NULL,
  `email_ufficio` varchar(120) NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_ufficio`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_uffici`
--

LOCK TABLES `tbl_uffici` WRITE;
/*!40000 ALTER TABLE `tbl_uffici` DISABLE KEYS */;
INSERT INTO `tbl_uffici` VALUES (1,'Ufficio Acquisti','Comando VV.F. Campobasso','comando.campobasso@vigilfuoco.it',_binary ''),(2,'Ufficio Informatico','Comando VV.F. Campobasso','ufficio.informatico@vigilfuoco.it',_binary ''),(3,'Ufficio Polizia Giudiziaria','Comando VV.F. Campobasso','pg@vigilfuoco.it',_binary ''),(4,'Ufficio Prevenzione Inccendi','Comando VV.F. Campobasso','previnc@vigilfuoco.it',_binary ''),(5,'Ufficio TLC','Comando VV.F. Campobasso','tlc@vigilfuoco.it',_binary ''),(6,'Ufficio Magazzino','Comando VV.F. Campobasso','magazzino@vigilfuoco.it',_binary '');
/*!40000 ALTER TABLE `tbl_uffici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_utenti`
--

DROP TABLE IF EXISTS `tbl_utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_utenti` (
  `id_utente` int unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(120) NOT NULL,
  `CF` char(16) NOT NULL,
  `classificazione_utente_app` int NOT NULL,
  `email_utente` varchar(50) NOT NULL,
  `attivo` bit(1) NOT NULL,
  PRIMARY KEY (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_utenti`
--

LOCK TABLES `tbl_utenti` WRITE;
/*!40000 ALTER TABLE `tbl_utenti` DISABLE KEYS */;
INSERT INTO `tbl_utenti` VALUES (1,'antonioroberto.diterlizzi','ANTONIO ROBERTO','DI TERLIZZI','DTRNNR85E11L109U',0,'antonioroberto.diterlizzi@vigilfuoco.it',_binary '\0'),(3,'simone.lecca','SIMONE','LECCA','LCCSMN97C11H856K',0,'simone.lecca@vigilfuoco.it',_binary '\0'),(4,'angelo.piccialli','ANGELO','PICCIALLI','PCCNGL74B15G224C',0,'angelo.piccialli@vigilfuoco.it',_binary '\0'),(5,'giovanni.mucciolo','GIOVANNI','MUCCIOLO','MCCGNN96B23A091G',0,'giovanni.mucciolo@vigilfuoco.it',_binary '\0'),(6,'maurizio.megna','MAURIZIO','MEGNA','MGNMRZ74A27H501U',0,'maurizio.megna@vigilfuoco.it',_binary '\0');
/*!40000 ALTER TABLE `tbl_utenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_utenti_uffici_ruoli`
--

DROP TABLE IF EXISTS `tbl_utenti_uffici_ruoli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_utenti_uffici_ruoli` (
  `id_utente_ufficio_ruolo` int unsigned NOT NULL AUTO_INCREMENT,
  `id_utente` int unsigned NOT NULL,
  `id_ruolo` int unsigned NOT NULL,
  `id_settore_ufficio` smallint unsigned NOT NULL,
  `attivo` bit(1) NOT NULL,
  `data_creazione` datetime NOT NULL,
  `data_disattivazione` datetime NOT NULL,
  PRIMARY KEY (`id_utente_ufficio_ruolo`),
  KEY `fk_tbl_utenti_has_tbl_uffici_tbl_utenti1_idx` (`id_utente`),
  KEY `fk_tbl_utenti_uffici_ruoli_tbl_ruoli1_idx` (`id_ruolo`),
  KEY `fk_tbl_utenti_uffici_ruoli_tbl_settori_uffici1_idx` (`id_settore_ufficio`),
  CONSTRAINT `fk_tbl_utenti_tbl_uffici_tbl_utenti` FOREIGN KEY (`id_utente`) REFERENCES `tbl_utenti` (`id_utente`),
  CONSTRAINT `fk_tbl_utenti_uffici_ruoli_tbl_ruoli` FOREIGN KEY (`id_ruolo`) REFERENCES `tbl_ruoli` (`id_ruolo`),
  CONSTRAINT `fk_tbl_utenti_uffici_ruoli_tbl_settori_uffici1` FOREIGN KEY (`id_settore_ufficio`) REFERENCES `tbl_settori_uffici` (`id_settore_ufficio`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_utenti_uffici_ruoli`
--

LOCK TABLES `tbl_utenti_uffici_ruoli` WRITE;
/*!40000 ALTER TABLE `tbl_utenti_uffici_ruoli` DISABLE KEYS */;
INSERT INTO `tbl_utenti_uffici_ruoli` VALUES (1,1,1,3,_binary '','2024-06-11 00:00:00','2024-06-11 00:00:00'),(2,1,4,5,_binary '','2024-07-23 00:00:00','2024-07-23 00:00:00'),(3,3,1,3,_binary '','2024-07-23 00:00:00','2024-07-23 00:00:00'),(4,3,1,6,_binary '','2024-07-23 00:00:00','2024-07-23 00:00:00'),(12,1,1,6,_binary '\0','2024-09-30 13:54:50','2074-09-30 13:54:50');
/*!40000 ALTER TABLE `tbl_utenti_uffici_ruoli` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-11  9:22:21
