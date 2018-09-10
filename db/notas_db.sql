-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 10-Set-2018 às 18:23
-- Versão do servidor: 10.1.35-MariaDB
-- versão do PHP: 7.1.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `unbgold_db`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_coluna`
--

CREATE TABLE `tb_coluna` (
  `id_coluna` int(11) NOT NULL,
  `id_dataset` int(11) NOT NULL,
  `nm_campo` varchar(255) NOT NULL,
  `publicar` tinyint(1) NOT NULL,
  `id_termo` int(11) NOT NULL,
  `objeto_literal` varchar(255) DEFAULT NULL,
  `complemento` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_coluna`
--

INSERT INTO `tb_coluna` (`id_coluna`, `id_dataset`, `nm_campo`, `publicar`, `id_termo`, `objeto_literal`, `complemento`) VALUES
(1, 1, 'codigo', 1, 13, 'codigo', 0),
(3, 1, 'sigla', 1, 13, 'Sigla', 0),
(4, 1, 'denominacao', 1, 9, 'Denominacao', 0),
(6, 1, 'site', 0, 13, NULL, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_dataset`
--

CREATE TABLE `tb_dataset` (
  `id_dataset` int(11) NOT NULL,
  `ds_dataset` varchar(255) NOT NULL,
  `fonte` varchar(255) NOT NULL,
  `parametros` varchar(255) NOT NULL,
  `iri` varchar(255) NOT NULL,
  `id_termo_tipo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_dataset`
--

INSERT INTO `tb_dataset` (`id_dataset`, `ds_dataset`, `fonte`, `parametros`, `iri`, `id_termo_tipo`) VALUES
(1, 'Conjunto de dados de departamento', 'http://localhost/dadosabertos/departamentos.csv', '{\"periodo\":\"2018/2\"}', '', 4),
(2, 'Conjunto de Dados de Cursos', 'http://localhost/dadosabertos/cursos.csv', '{\"periodo\":\"2018/2\"}', 'http://dadosabertos.unb.br/cursos/', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_nota`
--

CREATE TABLE `tb_nota` (
  `id` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descricao` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_nota`
--

INSERT INTO `tb_nota` (`id`, `titulo`, `descricao`) VALUES
(1, 'Nota 1', 'Nota um'),
(2, 'Nota 2 modificado', 'Nota dois modificado'),
(3, 'Nota 3 modificado', 'Nota tres modificado'),
(5, 'Nota 5', 'Nota cinco'),
(6, 'Nota 6', 'Nota seis'),
(7, 'Nota 7', 'Nota sete'),
(8, 'Nota 8', 'Nota oito'),
(9, 'Nota 9 ', 'Nota nove'),
(10, 'Nota 10 ', 'Nota dez');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_ontologia`
--

CREATE TABLE `tb_ontologia` (
  `id_ontologia` int(11) NOT NULL,
  `nm_ontologia` varchar(255) NOT NULL,
  `prefixo_ontologia` varchar(255) NOT NULL,
  `url_ontologia` varchar(255) NOT NULL,
  `comentario` varchar(3000) NOT NULL,
  `versao` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_ontologia`
--

INSERT INTO `tb_ontologia` (`id_ontologia`, `nm_ontologia`, `prefixo_ontologia`, `url_ontologia`, `comentario`, `versao`) VALUES
(1, 'Academic Institution Internal Structure Ontology', 'aiiso', 'http://purl.org/vocab/aiiso/schema', 'The Academic Institution Internal Structure Ontology (AIISO) provides classes and properties to describe the internal organizational structure of an academic institution. AIISO is designed to work in partnership with Participation (http://purl.org/vocab/participation/schema), FOAF (http://xmlns.com/foaf/0.1/) and aiiso-roles (http://purl.org/vocab/aiiso-roles/schema) to describe the roles that people play within an institution. [en]', '2008-09-25 '),
(2, 'DCMI Metadata Terms', 'dc', 'http://purl.org/dc/terms', 'This document is an up-to-date specification of all metadata terms maintained by the Dublin Core Metadata Initiative, including properties, vocabulary encoding schemes, syntax encoding schemes, and classes.', '2012-06-14'),
(3, 'Literal', 'literal', 'http://unb.br/literal', 'Metadados para auxiliar publicação de objetos literais', '0.9 beta');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_termo`
--

CREATE TABLE `tb_termo` (
  `id_termo` int(11) NOT NULL,
  `id_ontologia` int(11) NOT NULL,
  `nm_termo` varchar(255) NOT NULL,
  `iri_termo` varchar(511) NOT NULL,
  `comentario` varchar(3000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_termo`
--

INSERT INTO `tb_termo` (`id_termo`, `id_ontologia`, `nm_termo`, `iri_termo`, `comentario`) VALUES
(1, 1, 'College', 'http://purl.org/vocab/aiiso/schema#College', '	A College is a group of people recognised by an organization as forming a cohesive group referred to by the organization as a college'),
(2, 1, 'Center', 'http://purl.org/vocab/aiiso/schema#Center', 'A Center is a group of people recognised by an organization as forming a cohesive group referred to by the organization as a center'),
(3, 1, 'Course', 'http://purl.org/vocab/aiiso/schema#Course', 'A Course is a KnowledgeGrouping that represents a cohesive collection of educational material referred to by the owning organization as a course'),
(4, 1, 'Department', 'http://purl.org/vocab/aiiso/schema#Department', 'A Department is a group of people recognised by an organization as forming a cohesive group referred to by the organization as a department'),
(5, 1, 'Division', 'http://purl.org/vocab/aiiso/schema#Division', 'A Division is a group of people recognised by an organization as forming a cohesive group referred to by the organization as a division'),
(6, 1, 'Faculty', 'http://purl.org/vocab/aiiso/schema#Faculty', 'A Faculty is a group of people recognised by an organization as forming a cohesive group referred to by the organization as a faculty'),
(7, 1, 'Institute', 'http://purl.org/vocab/aiiso/schema#Institute', 'An Institute is a group of people recognised by an organization as forming a cohesive group referred to by the organization as an institute'),
(8, 1, 'Institution', 'http://purl.org/vocab/aiiso/schema#Institution', 'An Institution is the upper most level of an academic institution'),
(9, 2, 'Title', 'http://purl.org/dc/elements/1.1/title', 'A name given to the resource.'),
(10, 2, 'date', 'http://purl.org/dc/elements/1.1/date', 'Date may be used to express temporal information at any level of granularity. Recommended best practice is to use an encoding scheme, such as the W3CDTF profile of ISO 8601 [W3CDTF].'),
(11, 2, 'creator', 'http://purl.org/dc/elements/1.1/creator', 'Examples of a Creator include a person, an organization, or a service. Typically, the name of a Creator should be used to indicate the entity.'),
(12, 3, 'literal', 'http://unb.br/literal/', 'Termo para literal'),
(13, 3, 'naoindexado', 'http://unb.br/naoindexado/', 'Termo para campo não indexado');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_tripla`
--

CREATE TABLE `tb_tripla` (
  `id_tripla` int(11) NOT NULL,
  `id_dataset` int(11) NOT NULL,
  `sujeito` varchar(255) NOT NULL,
  `predicado` int(11) NOT NULL,
  `objeto` varchar(511) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_tripla`
--

INSERT INTO `tb_tripla` (`id_tripla`, `id_dataset`, `sujeito`, `predicado`, `objeto`) VALUES
(2, 1, 'http://cdt.unb.br', 13, '52'),
(3, 1, 'http://cdt.unb.brhttp://cdt.unb.br', 13, 'CDT'),
(4, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.br', 13, 'CENTRO DE APOIO AO DESENVOLVIMENTO TECNOLÓGICO'),
(5, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.br', 13, 'http://cdt.unb.br'),
(6, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.br', 13, '383'),
(7, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'CDS'),
(8, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'CENTRO DE DESENVOLVIMENTO SUSTENTÁVEL'),
(9, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'http://cds.unb.br'),
(10, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.br', 13, '25'),
(11, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'CEAM'),
(12, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'CENTRO DE ESTUDOS AVANÇADOS E MULTIDISCIPLINARES'),
(13, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'http://ceam.unb.br'),
(14, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://cet.unb.br', 13, '39'),
(15, 1, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://cet.unb.brhttp://cet.unb.br', 13, 'CET');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_coluna`
--
ALTER TABLE `tb_coluna`
  ADD PRIMARY KEY (`id_coluna`),
  ADD KEY `dataset` (`id_dataset`),
  ADD KEY `termo` (`id_termo`);

--
-- Indexes for table `tb_dataset`
--
ALTER TABLE `tb_dataset`
  ADD PRIMARY KEY (`id_dataset`),
  ADD KEY `id_termo_tipo` (`id_termo_tipo`);

--
-- Indexes for table `tb_nota`
--
ALTER TABLE `tb_nota`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_ontologia`
--
ALTER TABLE `tb_ontologia`
  ADD PRIMARY KEY (`id_ontologia`);

--
-- Indexes for table `tb_termo`
--
ALTER TABLE `tb_termo`
  ADD PRIMARY KEY (`id_termo`),
  ADD KEY `id_ontologia` (`id_ontologia`);

--
-- Indexes for table `tb_tripla`
--
ALTER TABLE `tb_tripla`
  ADD PRIMARY KEY (`id_tripla`),
  ADD KEY `predicado` (`predicado`),
  ADD KEY `dataset_tripla` (`id_dataset`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_coluna`
--
ALTER TABLE `tb_coluna`
  MODIFY `id_coluna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tb_dataset`
--
ALTER TABLE `tb_dataset`
  MODIFY `id_dataset` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_nota`
--
ALTER TABLE `tb_nota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tb_ontologia`
--
ALTER TABLE `tb_ontologia`
  MODIFY `id_ontologia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_termo`
--
ALTER TABLE `tb_termo`
  MODIFY `id_termo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tb_tripla`
--
ALTER TABLE `tb_tripla`
  MODIFY `id_tripla` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tb_coluna`
--
ALTER TABLE `tb_coluna`
  ADD CONSTRAINT `dataset` FOREIGN KEY (`id_dataset`) REFERENCES `tb_dataset` (`id_dataset`),
  ADD CONSTRAINT `termo` FOREIGN KEY (`id_termo`) REFERENCES `tb_termo` (`id_termo`);

--
-- Limitadores para a tabela `tb_dataset`
--
ALTER TABLE `tb_dataset`
  ADD CONSTRAINT `tb_dataset_ibfk_1` FOREIGN KEY (`id_termo_tipo`) REFERENCES `tb_termo` (`id_termo`);

--
-- Limitadores para a tabela `tb_termo`
--
ALTER TABLE `tb_termo`
  ADD CONSTRAINT `tb_termo_ibfk_1` FOREIGN KEY (`id_ontologia`) REFERENCES `tb_ontologia` (`id_ontologia`);

--
-- Limitadores para a tabela `tb_tripla`
--
ALTER TABLE `tb_tripla`
  ADD CONSTRAINT `dataset_tripla` FOREIGN KEY (`id_dataset`) REFERENCES `tb_dataset` (`id_dataset`),
  ADD CONSTRAINT `predicado` FOREIGN KEY (`predicado`) REFERENCES `tb_termo` (`id_termo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
