-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 03-Out-2018 às 19:42
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
  `complemento` tinyint(1) NOT NULL,
  `id_coluna_ligacao` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_coluna`
--

INSERT INTO `tb_coluna` (`id_coluna`, `id_dataset`, `nm_campo`, `publicar`, `id_termo`, `objeto_literal`, `complemento`, `id_coluna_ligacao`) VALUES
(1, 1, 'NaoIndexado', 1, 13, '1', 0, 1),
(3, 3, 'sigla', 1, 13, 'Sigla', 0, 1),
(4, 3, 'denominacao', 1, 9, 'Denominacao', 0, 1),
(6, 3, 'site', 0, 13, NULL, 1, 1),
(7, 2, 'Cod_Curso', 0, 13, 'Cod_Curso', 1, 1),
(8, 2, 'Curso', 1, 9, 'Curso', 0, 1),
(9, 2, 'Nome_orgao', 1, 4, '0', 0, 4),
(10, 3, 'codigo', 1, 4, '1', 0, 1);

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
  `id_termo_tipo` int(11) DEFAULT NULL,
  `concluido` tinyint(1) NOT NULL,
  `id_termo` int(11) NOT NULL,
  `automatizada` tinyint(1) NOT NULL DEFAULT '1',
  `indexar_semantica` tinyint(1) NOT NULL DEFAULT '1',
  `id_instancia_ckan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_dataset`
--

INSERT INTO `tb_dataset` (`id_dataset`, `ds_dataset`, `fonte`, `parametros`, `iri`, `id_termo_tipo`, `concluido`, `id_termo`, `automatizada`, `indexar_semantica`, `id_instancia_ckan`) VALUES
(1, 'Não Indexado', 'http://www.unb.br/naoindexado', '{\"periodo\":\"2018/2\"}', 'http://www.unb.br/naoindexado', 13, 1, 13, 0, 0, 1),
(2, 'Conjunto de Dados de Cursos', 'http://localhost/dadosabertos/cursos.csv', '{\"periodo\":\"2018/2\"}', 'http://dadosabertos.unb.br/cursos/', 3, 1, 3, 1, 1, 2),
(3, 'Conjunto de dados de departamento', 'http://localhost/dadosabertos/departamentos.csv', '', '', 4, 1, 4, 1, 1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_instancia_ckan`
--

CREATE TABLE `tb_instancia_ckan` (
  `id_instancia_ckan` int(11) NOT NULL,
  `id_unidade_publicadora` int(11) NOT NULL,
  `desc_instancia_ckan` varchar(255) NOT NULL,
  `key_api` varchar(255) NOT NULL,
  `endereco_ckan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_instancia_ckan`
--

INSERT INTO `tb_instancia_ckan` (`id_instancia_ckan`, `id_unidade_publicadora`, `desc_instancia_ckan`, `key_api`, `endereco_ckan`) VALUES
(1, 1, 'Instância  não automatizada', 'AAAAAAAAAAAAAAAAAAAAA', 'http://127.0.0.1:5000/'),
(2, 1, 'Dados Abertos da UnB', '28e50ca6-8ba8-464a-bf8f-72d2fec0554a', 'http://localhost:5000/');

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
-- Estrutura da tabela `tb_objeto`
--

CREATE TABLE `tb_objeto` (
  `id_objeto` int(11) NOT NULL,
  `id_sujeito` int(11) NOT NULL,
  `id_termo` int(11) NOT NULL,
  `id_objeto_tipo` int(11) NOT NULL,
  `desc_objeto` varchar(100) NOT NULL,
  `id_coluna` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_objeto`
--

INSERT INTO `tb_objeto` (`id_objeto`, `id_sujeito`, `id_termo`, `id_objeto_tipo`, `desc_objeto`, `id_coluna`) VALUES
(1, 1894, 13, 1, 'ADM', 3),
(2, 1894, 9, 1, 'Departamento de Administração', 4),
(3, 1894, 4, 1, '8117', 10),
(4, 1895, 13, 1, 'CCA', 3),
(5, 1895, 9, 1, 'Depto de Ciências Contábeis e Atuariais', 4),
(6, 1895, 4, 1, '8516', 10),
(7, 1896, 13, 1, 'CEN', 3),
(8, 1896, 9, 1, 'Departamento de Artes Cênicas', 4),
(9, 1896, 4, 1, '5371', 10),
(10, 1897, 13, 1, 'CET', 3),
(11, 1897, 9, 1, 'Centro de Excelência em Turismo', 4),
(12, 1897, 4, 1, '6271', 10),
(13, 1898, 13, 1, 'CIC', 3),
(14, 1898, 9, 1, 'Departamento de Ciência da Computação', 4),
(15, 1898, 4, 1, '1856', 10),
(16, 1899, 13, 1, 'DIN', 3),
(17, 1899, 9, 1, 'Departamento de Desenho Industrial', 4),
(18, 1899, 4, 1, '5380', 10),
(19, 1900, 13, 1, 'DSC', 3),
(20, 1900, 9, 1, 'Departamento de Saúde Coletiva', 4),
(21, 1900, 4, 1, '7161', 10),
(22, 1901, 13, 1, 'ECO', 3),
(23, 1901, 9, 1, 'Departamento de Economia', 4),
(24, 1901, 4, 1, '3221', 10),
(25, 1902, 13, 1, 'EFL', 3),
(26, 1902, 9, 1, 'Departamento de Engenharia Florestal', 4),
(27, 1902, 4, 1, '6521', 10),
(28, 1903, 13, 1, 'ENC', 3),
(29, 1903, 9, 1, 'Departamento de Engenharia Civil e Ambiental', 4),
(30, 1903, 4, 1, '6220', 10),
(31, 1904, 13, 1, 'ENE', 3),
(32, 1904, 9, 1, 'Departamento de Engenharia Elétrica.', 4),
(33, 1904, 4, 1, '6335', 10),
(34, 1905, 13, 1, 'ENF', 3),
(35, 1905, 9, 1, 'Departamento de Enfermagem', 4),
(36, 1905, 4, 1, '7412', 10),
(37, 1906, 13, 1, 'ENM', 3),
(38, 1906, 9, 1, 'Departamento de Engenharia Mecânica', 4),
(39, 1906, 4, 1, '6424', 10),
(40, 1907, 13, 1, 'EST', 3),
(41, 1907, 9, 1, 'Departamento de Estatística', 4),
(42, 1907, 4, 1, '1716', 10),
(43, 1908, 13, 1, 'FAC', 3),
(44, 1908, 9, 1, 'Faculdade de Comunicação', 4),
(45, 1908, 4, 1, '8338', 10),
(46, 1909, 13, 1, 'FACE', 3),
(47, 1909, 9, 1, 'Faculd. de Economia, Administração e Contabilidade', 4),
(48, 1909, 4, 1, '8176', 10),
(49, 1910, 13, 1, 'FAU', 3),
(50, 1910, 9, 1, 'Direção da Faculdade de Arquitetura e Urbanismo', 4),
(51, 1910, 4, 1, '5126', 10),
(52, 1911, 13, 1, 'FAV', 3),
(53, 1911, 9, 1, 'Faculdade de Agronomia e Medicina Veterinária', 4),
(54, 1911, 4, 1, '6181', 10),
(55, 1912, 13, 1, 'FCE', 3),
(56, 1912, 9, 1, 'UnB - Faculdade da Ceilândia', 4),
(57, 1912, 4, 1, '7013', 10),
(58, 1913, 13, 1, 'FCI', 3),
(59, 1913, 9, 1, 'Faculdade de Ciência da Informação', 4),
(60, 1913, 4, 1, '8222', 10),
(61, 1914, 13, 1, 'FDD', 3),
(62, 1914, 9, 1, 'Faculdade de Direito', 4),
(63, 1914, 4, 1, '8486', 10),
(64, 1915, 13, 1, 'FE', 3),
(65, 1915, 9, 1, 'Faculdade de Educação', 4),
(66, 1915, 4, 1, '9229', 10),
(67, 1916, 13, 1, 'FEF', 3),
(68, 1916, 9, 1, 'Faculdade de Educação Física', 4),
(69, 1916, 4, 1, '7315', 10),
(70, 1917, 13, 1, 'FGA', 3),
(71, 1917, 9, 1, 'UnB - Faculdade do Gama', 4),
(72, 1917, 4, 1, '6009', 10),
(73, 1918, 13, 1, 'FIL', 3),
(74, 1918, 9, 1, 'Departamento de Filosofia', 4),
(75, 1918, 4, 1, '3336', 10),
(76, 1919, 13, 1, 'FMD', 3),
(77, 1919, 9, 1, 'Faculdade de Medicina', 4),
(78, 1919, 4, 1, '7111', 10),
(79, 1920, 13, 1, 'FS', 3),
(80, 1920, 9, 1, 'Faculdade de Ciências da Saúde', 4),
(81, 1920, 4, 1, '7692', 10),
(82, 1921, 13, 1, 'FT', 3),
(83, 1921, 9, 1, 'Faculdade de Tecnologia', 4),
(84, 1921, 4, 1, '6351', 10),
(85, 1922, 13, 1, 'FUP', 3),
(86, 1922, 9, 1, 'UnB - Faculdade de Planaltina', 4),
(87, 1922, 4, 1, '6190', 10),
(88, 1923, 13, 1, 'GEA', 3),
(89, 1923, 9, 1, 'Departamento de Geografia', 4),
(90, 1923, 4, 1, '3841', 10),
(91, 1924, 13, 1, 'HIS', 3),
(92, 1924, 9, 1, 'Departamento de História', 4),
(93, 1924, 4, 1, '3417', 10),
(94, 1925, 13, 1, 'IB', 3),
(95, 1925, 9, 1, 'Instituto de Ciências Biológicas', 4),
(96, 1925, 4, 1, '2216', 10),
(97, 1926, 13, 1, 'ICS', 3),
(98, 1926, 9, 1, 'Instituto de Ciências Sociais', 4),
(99, 1926, 4, 1, '3115', 10),
(100, 1927, 13, 1, 'IDA', 3),
(101, 1927, 9, 1, 'Instituto de Artes', 4),
(102, 1927, 4, 1, '451', 10),
(103, 1928, 13, 1, 'IFD', 3),
(104, 1928, 9, 1, 'Instituto de Física', 4),
(105, 1928, 4, 1, '1112', 10),
(106, 1929, 13, 1, 'IGD', 3),
(107, 1929, 9, 1, 'Instituto de Geociências', 4),
(108, 1929, 4, 1, '1228', 10),
(109, 1930, 13, 1, 'IH', 3),
(110, 1930, 9, 1, 'Instituto de Ciências Humanas', 4),
(111, 1930, 4, 1, '3867', 10),
(112, 1931, 13, 1, 'IL', 3),
(113, 1931, 9, 1, 'Instituto de Letras', 4),
(114, 1931, 4, 1, '4111', 10),
(115, 1932, 13, 1, 'IP', 3),
(116, 1932, 9, 1, 'Direção do Instituto de Psicologia', 4),
(117, 1932, 4, 1, '2712', 10),
(118, 1933, 13, 1, 'IPOL', 3),
(119, 1933, 9, 1, 'Instituto de Ciência Política', 4),
(120, 1933, 4, 1, '8591', 10),
(121, 1934, 13, 1, 'IQD', 3),
(122, 1934, 9, 1, 'Instituto de Química', 4),
(123, 1934, 4, 1, '1449', 10),
(124, 1935, 13, 1, 'IREL', 3),
(125, 1935, 9, 1, 'Instituto de Relações Internacionais', 4),
(126, 1935, 4, 1, '3727', 10),
(127, 1936, 13, 1, 'LET', 3),
(128, 1936, 9, 1, 'Departamento de Línguas Estrangeiras e Tradução', 4),
(129, 1936, 4, 1, '4090', 10),
(130, 1937, 13, 1, 'LIP', 3),
(131, 1937, 9, 1, 'Departamento de Lingüística, Português, Líng Clás', 4),
(132, 1937, 4, 1, '698', 10),
(133, 1938, 13, 1, 'MAT', 3),
(134, 1938, 9, 1, 'Departamento de Matemática.', 4),
(135, 1938, 4, 1, '1325', 10),
(136, 1939, 13, 1, 'MUS', 3),
(137, 1939, 9, 1, 'Departamento de Música', 4),
(138, 1939, 4, 1, '5223', 10),
(139, 1940, 13, 1, 'NUT', 3),
(140, 1940, 9, 1, 'Departamento de Nutrição', 4),
(141, 1940, 4, 1, '7510', 10),
(142, 1941, 13, 1, 'ODT', 3),
(143, 1941, 9, 1, 'Departamento de Odontologia', 4),
(144, 1941, 4, 1, '7218', 10),
(145, 1942, 13, 1, 'SER', 3),
(146, 1942, 9, 1, 'Departamento de Serviço Social', 4),
(147, 1942, 4, 1, '3514', 10),
(148, 1943, 13, 1, 'VIS', 3),
(149, 1943, 9, 1, 'Departamento de Artes Visuais', 4),
(150, 1943, 4, 1, '5649', 10),
(151, 1944, 9, 1, 'Administração', 8),
(152, 1944, 4, 2, 'Departamento de Administração', 9),
(153, 1945, 9, 1, 'Arquitetura e Urbanismo', 8),
(154, 1945, 4, 2, 'Direção da Faculdade de Arquitetura e Urbanismo', 9),
(155, 1946, 9, 1, 'Ciências Econômicas', 8),
(156, 1946, 4, 2, 'Departamento de Economia', 9),
(157, 1947, 9, 1, 'Direito', 8),
(158, 1947, 4, 2, 'Faculdade de Direito', 9),
(159, 1948, 9, 1, 'Letras', 8),
(160, 1948, 4, 2, 'Instituto de Letras', 9),
(161, 1949, 9, 1, 'Pedagogia', 8),
(162, 1949, 4, 2, 'Faculdade de Educação', 9),
(163, 1950, 9, 1, 'Ciências Biológicas', 8),
(164, 1950, 4, 2, 'Instituto de Ciências Biológicas', 9),
(165, 1951, 9, 1, 'Física', 8),
(166, 1951, 4, 2, 'Instituto de Física', 9),
(167, 1952, 9, 1, 'Geologia', 8),
(168, 1952, 4, 2, 'Instituto de Geociências', 9),
(169, 1953, 9, 1, 'Matemática', 8),
(170, 1953, 4, 2, 'Departamento de Matemática.', 9),
(171, 1954, 9, 1, 'Medicina', 8),
(172, 1954, 4, 2, 'Faculdade de Medicina', 9),
(173, 1955, 9, 1, 'Química', 8),
(174, 1955, 4, 2, 'Instituto de Química', 9),
(175, 1956, 9, 1, 'Música', 8),
(176, 1956, 4, 2, 'Departamento de Música', 9),
(177, 1957, 9, 1, 'Psicologia', 8),
(178, 1957, 4, 2, 'Direção do Instituto de Psicologia', 9),
(179, 1958, 9, 1, 'Biblioteconomia', 8),
(180, 1958, 4, 2, 'Faculdade de Ciência da Informação', 9),
(181, 1959, 9, 1, 'Comunicação Social', 8),
(182, 1959, 4, 2, 'Faculdade de Comunicação', 9),
(183, 1960, 9, 1, 'Ciências Sociais', 8),
(184, 1960, 4, 2, 'Instituto de Ciências Sociais', 9),
(185, 1961, 9, 1, 'Filosofia', 8),
(186, 1961, 4, 2, 'Departamento de Filosofia', 9),
(187, 1962, 9, 1, 'História', 8),
(188, 1962, 4, 2, 'Departamento de História', 9),
(189, 1963, 9, 1, 'Geografia', 8),
(190, 1963, 4, 2, 'Departamento de Geografia', 9),
(191, 1964, 9, 1, 'Serviço Social', 8),
(192, 1964, 4, 2, 'Departamento de Serviço Social', 9),
(193, 1965, 9, 1, 'Música', 8),
(194, 1965, 4, 2, 'Departamento de Música', 9),
(195, 1966, 9, 1, 'Teatro', 8),
(196, 1966, 4, 2, 'Instituto de Artes', 9),
(197, 1967, 9, 1, 'Educação Física', 8),
(198, 1967, 4, 2, 'Faculdade de Educação Física', 9),
(199, 1968, 9, 1, 'Jornalismo', 8),
(200, 1968, 4, 2, 'Faculdade de Comunicação', 9),
(201, 1969, 9, 1, 'Língua de Sinais Brasileira/Português como Segunda Língua', 8),
(202, 1969, 4, 2, 'Departamento de Lingüística, Português, Líng Clás', 9),
(203, 1970, 9, 1, 'Estatística', 8),
(204, 1970, 4, 2, 'Departamento de Estatística', 9),
(205, 1971, 9, 1, 'Ciência da Computação', 8),
(206, 1971, 4, 2, 'Departamento de Ciência da Computação', 9),
(207, 1972, 9, 1, 'Física', 8),
(208, 1972, 4, 2, 'Instituto de Física', 9),
(209, 1973, 9, 1, 'Engenharia Florestal', 8),
(210, 1973, 4, 2, 'Departamento de Engenharia Florestal', 9),
(211, 1974, 9, 1, 'Relações Internacionais', 8),
(212, 1974, 4, 2, 'Instituto de Relações Internacionais', 9),
(213, 1975, 9, 1, 'Enfermagem', 8),
(214, 1975, 4, 2, 'Departamento de Enfermagem', 9),
(215, 1976, 9, 1, 'Nutrição', 8),
(216, 1976, 4, 2, 'Departamento de Nutrição', 9),
(217, 1977, 9, 1, 'Ciências Contábeis', 8),
(218, 1977, 4, 2, 'Depto de Ciências Contábeis e Atuariais', 9),
(219, 1978, 9, 1, 'Engenharia Civil', 8),
(220, 1978, 4, 2, 'Departamento de Engenharia Civil e Ambiental', 9),
(221, 1979, 9, 1, 'Engenharia Elétrica', 8),
(222, 1979, 4, 2, 'Departamento de Engenharia Elétrica.', 9),
(223, 1980, 9, 1, 'Engenharia Mecânica', 8),
(224, 1980, 4, 2, 'Departamento de Engenharia Mecânica', 9),
(225, 1981, 9, 1, 'Letras-Tradução', 8),
(226, 1981, 4, 2, 'Instituto de Letras', 9),
(227, 1982, 9, 1, 'Odontologia', 8),
(228, 1982, 4, 2, 'Departamento de Odontologia', 9),
(229, 1983, 9, 1, 'Artes Visuais', 8),
(230, 1983, 4, 2, 'Departamento de Artes Visuais', 9),
(231, 1984, 9, 1, 'Artes Cênicas', 8),
(232, 1984, 4, 2, 'Departamento de Artes Cênicas', 9),
(233, 1985, 9, 1, 'Design', 8),
(234, 1985, 4, 2, 'Departamento de Desenho Industrial', 9),
(235, 1986, 9, 1, 'Administração', 8),
(236, 1986, 4, 2, 'Departamento de Administração', 9),
(237, 1987, 9, 1, 'Ciência Política', 8),
(238, 1987, 4, 2, 'Instituto de Ciência Política', 9),
(239, 1988, 9, 1, 'Arquivologia', 8),
(240, 1988, 4, 2, 'Faculdade de Ciência da Informação', 9),
(241, 1989, 9, 1, 'Ciências Biológicas', 8),
(242, 1989, 4, 2, 'Instituto de Ciências Biológicas', 9),
(243, 1990, 9, 1, 'Física', 8),
(244, 1990, 4, 2, 'Instituto de Física', 9),
(245, 1991, 9, 1, 'Matemática', 8),
(246, 1991, 4, 2, 'Departamento de Matemática.', 9),
(247, 1992, 9, 1, 'Química', 8),
(248, 1992, 4, 2, 'Instituto de Química', 9),
(249, 1993, 9, 1, 'Letras', 8),
(250, 1993, 4, 2, 'Instituto de Letras', 9),
(251, 1994, 9, 1, 'Pedagogia', 8),
(252, 1994, 4, 2, 'Faculdade de Educação', 9),
(253, 1995, 9, 1, 'Ciências Contábeis', 8),
(254, 1995, 4, 2, 'Depto de Ciências Contábeis e Atuariais', 9),
(255, 1996, 9, 1, 'Direito', 8),
(256, 1996, 4, 2, 'Faculdade de Direito', 9),
(257, 1997, 9, 1, 'Letras', 8),
(258, 1997, 4, 2, 'Instituto de Letras', 9),
(259, 1998, 9, 1, 'Farmácia', 8),
(260, 1998, 4, 2, 'Faculdade de Ciências da Saúde', 9),
(261, 1999, 9, 1, 'Medicina Veterinária', 8),
(262, 1999, 4, 2, 'Faculdade de Agronomia e Medicina Veterinária', 9),
(263, 2000, 9, 1, 'Engenharia de Redes de Comunicação', 8),
(264, 2000, 4, 2, 'Faculdade de Tecnologia', 9),
(265, 2001, 9, 1, 'Computação', 8),
(266, 2001, 4, 2, 'Departamento de Ciência da Computação', 9),
(267, 2002, 9, 1, 'Letras', 8),
(268, 2002, 4, 2, 'Instituto de Letras', 9),
(269, 2003, 9, 1, 'Engenharia Mecatrônica', 8),
(270, 2003, 4, 2, 'Faculdade de Tecnologia', 9),
(271, 2004, 9, 1, 'Artes Visuais', 8),
(272, 2004, 4, 2, 'Departamento de Artes Visuais', 9),
(273, 2005, 9, 1, 'Gestão do Agronegócio', 8),
(274, 2005, 4, 2, 'UnB - Faculdade de Planaltina', 9),
(275, 2006, 9, 1, 'Ciências Naturais', 8),
(276, 2006, 4, 2, 'UnB - Faculdade de Planaltina', 9),
(277, 2007, 9, 1, 'Administração', 8),
(278, 2007, 4, 2, 'Faculd. de Economia, Administração e Contabilidade', 9),
(279, 2008, 9, 1, 'Educação do Campo', 8),
(280, 2008, 4, 2, 'UnB - Faculdade de Planaltina', 9),
(281, 2009, 9, 1, 'Música', 8),
(282, 2009, 4, 2, 'Instituto de Artes', 9),
(283, 2010, 9, 1, 'Letras', 8),
(284, 2010, 4, 2, 'Instituto de Letras', 9),
(285, 2011, 9, 1, 'Artes Visuais', 8),
(286, 2011, 4, 2, 'Instituto de Artes', 9),
(287, 2012, 9, 1, 'Educação Física', 8),
(288, 2012, 4, 2, 'Faculdade de Educação Física', 9),
(289, 2013, 9, 1, 'Teatro', 8),
(290, 2013, 4, 2, 'Instituto de Artes', 9),
(291, 2014, 9, 1, 'Pedagogia', 8),
(292, 2014, 4, 2, 'Faculdade de Educação', 9),
(293, 2015, 9, 1, 'Engenharia', 8),
(294, 2015, 4, 2, 'UnB - Faculdade do Gama', 9),
(295, 2016, 9, 1, 'Farmácia', 8),
(296, 2016, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(297, 2017, 9, 1, 'Enfermagem', 8),
(298, 2017, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(299, 2018, 9, 1, 'Fisioterapia', 8),
(300, 2018, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(301, 2019, 9, 1, 'Terapia Ocupacional', 8),
(302, 2019, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(303, 2020, 9, 1, 'Saúde Coletiva', 8),
(304, 2020, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(305, 2021, 9, 1, 'Gestão Ambiental', 8),
(306, 2021, 4, 2, 'UnB - Faculdade de Planaltina', 9),
(307, 2022, 9, 1, 'Ciências Naturais', 8),
(308, 2022, 4, 2, 'UnB - Faculdade de Planaltina', 9),
(309, 2023, 9, 1, 'Artes Cênicas', 8),
(310, 2023, 4, 2, 'Departamento de Artes Cênicas', 9),
(311, 2024, 9, 1, 'Geografia', 8),
(312, 2024, 4, 2, 'Instituto de Ciências Humanas', 9),
(313, 2025, 9, 1, 'Ciências Biológicas', 8),
(314, 2025, 4, 2, 'Instituto de Ciências Biológicas', 9),
(315, 2026, 9, 1, 'Ciências Ambientais', 8),
(316, 2026, 4, 2, 'Instituto de Geociências', 9),
(317, 2027, 9, 1, 'Engenharia de Computação', 8),
(318, 2027, 4, 2, 'Departamento de Ciência da Computação', 9),
(319, 2028, 9, 1, 'Gestão de Políticas Públicas', 8),
(320, 2028, 4, 2, 'Faculd. de Economia, Administração e Contabilidade', 9),
(321, 2029, 9, 1, 'Museologia', 8),
(322, 2029, 4, 2, 'Faculdade de Ciência da Informação', 9),
(323, 2030, 9, 1, 'Engenharia de Produção', 8),
(324, 2030, 4, 2, 'Faculdade de Tecnologia', 9),
(325, 2031, 9, 1, 'Geofísica', 8),
(326, 2031, 4, 2, 'Instituto de Geociências', 9),
(327, 2032, 9, 1, 'História', 8),
(328, 2032, 4, 2, 'Departamento de História', 9),
(329, 2033, 9, 1, 'Música', 8),
(330, 2033, 4, 2, 'Departamento de Música', 9),
(331, 2034, 9, 1, 'Letras-Tradução Espanhol', 8),
(332, 2034, 4, 2, 'Instituto de Letras', 9),
(333, 2035, 9, 1, 'Arquitetura e Urbanismo', 8),
(334, 2035, 4, 2, 'Direção da Faculdade de Arquitetura e Urbanismo', 9),
(335, 2036, 9, 1, 'Saúde Coletiva', 8),
(336, 2036, 4, 2, 'Departamento de Saúde Coletiva', 9),
(337, 2037, 9, 1, 'Farmácia', 8),
(338, 2037, 4, 2, 'Faculdade de Ciências da Saúde', 9),
(339, 2038, 9, 1, 'Engenharia Ambiental', 8),
(340, 2038, 4, 2, 'Departamento de Engenharia Civil e Ambiental', 9),
(341, 2039, 9, 1, 'Línguas Estrangeiras Aplicadas - MSI', 8),
(342, 2039, 4, 2, 'Departamento de Línguas Estrangeiras e Tradução', 9),
(343, 2040, 9, 1, 'Comunicação Social', 8),
(344, 2040, 4, 2, 'Faculdade de Comunicação', 9),
(345, 2041, 9, 1, 'Química Tecnológica', 8),
(346, 2041, 4, 2, 'Instituto de Química', 9),
(347, 2042, 9, 1, 'Serviço Social', 8),
(348, 2042, 4, 2, 'Departamento de Serviço Social', 9),
(349, 2043, 9, 1, 'Filosofia', 8),
(350, 2043, 4, 2, 'Departamento de Filosofia', 9),
(351, 2044, 9, 1, 'Gestão de Agronegócios', 8),
(352, 2044, 4, 2, 'Faculdade de Agronomia e Medicina Veterinária', 9),
(353, 2045, 9, 1, 'Turismo', 8),
(354, 2045, 4, 2, 'Centro de Excelência em Turismo', 9),
(355, 2046, 9, 1, 'Biotecnologia', 8),
(356, 2046, 4, 2, 'Instituto de Ciências Biológicas', 9),
(357, 2047, 9, 1, 'Administração Pública', 8),
(358, 2047, 4, 2, 'Departamento de Administração', 9),
(359, 2048, 9, 1, 'Teoria, Crítica e História da Arte', 8),
(360, 2048, 4, 2, 'Instituto de Artes', 9),
(361, 2049, 9, 1, 'Engenharia Química', 8),
(362, 2049, 4, 2, 'Instituto de Química', 9),
(363, 2050, 9, 1, 'Fonoaudiologia', 8),
(364, 2050, 4, 2, 'UnB - Faculdade da Ceilândia', 9),
(365, 2051, 9, 1, 'Educação Física', 8),
(366, 2051, 4, 2, 'Faculdade de Educação Física', 9),
(367, 2052, 9, 1, 'Engenharia Eletrônica', 8),
(368, 2052, 4, 2, 'UnB - Faculdade do Gama', 9),
(369, 2053, 9, 1, 'Engenharia de Energia', 8),
(370, 2053, 4, 2, 'UnB - Faculdade do Gama', 9),
(371, 2054, 9, 1, 'Engenharia Automotiva', 8),
(372, 2054, 4, 2, 'UnB - Faculdade do Gama', 9),
(373, 2055, 9, 1, 'Engenharia de Software', 8),
(374, 2055, 4, 2, 'UnB - Faculdade do Gama', 9),
(375, 2056, 9, 1, 'Engenharia Aeroespacial', 8),
(376, 2056, 4, 2, 'UnB - Faculdade do Gama', 9);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_objeto_tipo`
--

CREATE TABLE `tb_objeto_tipo` (
  `id_objeto_tipo` int(11) NOT NULL,
  `desc_objeto_tipo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_objeto_tipo`
--

INSERT INTO `tb_objeto_tipo` (`id_objeto_tipo`, `desc_objeto_tipo`) VALUES
(1, 'Literal'),
(2, 'Recurso');

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
-- Estrutura da tabela `tb_parametro`
--

CREATE TABLE `tb_parametro` (
  `id_parametro` int(11) NOT NULL,
  `id_dataset` int(11) NOT NULL,
  `chave_parametro` varchar(255) NOT NULL,
  `valor_parametro` varchar(255) DEFAULT NULL,
  `id_parametro_tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_parametro`
--

INSERT INTO `tb_parametro` (`id_parametro`, `id_dataset`, `chave_parametro`, `valor_parametro`, `id_parametro_tipo`) VALUES
(1, 1, 'semestre', '20182', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_parametro_tipo`
--

CREATE TABLE `tb_parametro_tipo` (
  `id_parametro_tipo` int(11) NOT NULL,
  `desc_parametro_tipo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_parametro_tipo`
--

INSERT INTO `tb_parametro_tipo` (`id_parametro_tipo`, `desc_parametro_tipo`) VALUES
(1, 'Fixo'),
(2, 'Hoje');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_publicacao`
--

CREATE TABLE `tb_publicacao` (
  `id_publicacao` int(11) NOT NULL,
  `data_publicacao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ativo` tinyint(1) NOT NULL,
  `id_dataset` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_publicacao`
--

INSERT INTO `tb_publicacao` (`id_publicacao`, `data_publicacao`, `ativo`, `id_dataset`) VALUES
(1, '2018-09-15 14:15:23', 1, 3),
(2, '2018-09-25 23:15:15', 1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_sujeito`
--

CREATE TABLE `tb_sujeito` (
  `id_sujeito` int(11) NOT NULL,
  `id_publicacao` int(11) NOT NULL,
  `desc_sujeito` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_sujeito`
--

INSERT INTO `tb_sujeito` (`id_sujeito`, `id_publicacao`, `desc_sujeito`) VALUES
(1894, 1, 'http://www.adm.unb.br'),
(1895, 1, 'http://www.cca.unb.br'),
(1896, 1, 'http://www.artescenicas.unb.br'),
(1897, 1, 'http://www.cet.unb.br'),
(1898, 1, 'http://www.cic.unb.br'),
(1899, 1, 'http://www.dei.unb.br'),
(1900, 1, 'http://www.dsc.unb.br'),
(1901, 1, 'http://www.economia.unb.br'),
(1902, 1, 'http://www.engenhariaflorestal.unb.br'),
(1903, 1, 'http://www.deca.unb.br'),
(1904, 1, 'http://www.ee.unb.br'),
(1905, 1, 'http://www.enfermagem.unb.br'),
(1906, 1, 'http://www.mecanica.unb.br'),
(1907, 1, 'http://www.estatitica.unb.br'),
(1908, 1, 'http://www.comunicacao.unb.br'),
(1909, 1, 'http://www.eac.unb.br'),
(1910, 1, 'http://www.arquitetura.unb.br'),
(1911, 1, 'http://www.veterinaria.unb.br'),
(1912, 1, 'http://www.fce.unb.br'),
(1913, 1, 'http://www.fci.unb.br'),
(1914, 1, 'http://www.direito.unb.br'),
(1915, 1, 'http://www.fe.unb.br'),
(1916, 1, 'http://www.fef.unb.br'),
(1917, 1, 'http://www.fga.unb.br'),
(1918, 1, 'http://www.filosofia.unb.br'),
(1919, 1, 'http://www.medicina.unb.br'),
(1920, 1, 'http://www.fs.unb.br'),
(1921, 1, 'http://www.ft.unb.br'),
(1922, 1, 'http://www.fp.unb.br'),
(1923, 1, 'http://www.geografia.unb.br'),
(1924, 1, 'http://www.historia.unb.br'),
(1925, 1, 'http://www.ib.unb.br'),
(1926, 1, 'http://www.ics.unb.br'),
(1927, 1, 'http://www.artes.unb.br'),
(1928, 1, 'http://www.fisica.unb.br'),
(1929, 1, 'http://www.geociencias.unb.br'),
(1930, 1, 'http://www.ich.unb.br'),
(1931, 1, 'http://www.il.unb.br'),
(1932, 1, 'http://www.ip.unb.br'),
(1933, 1, 'http://www.cienciapolitica.unb.br'),
(1934, 1, 'http://www.quimica.unb.br'),
(1935, 1, 'http://www.relacoesinternacionais.unb.br'),
(1936, 1, 'http://www.linhaestrangeira.unb.br'),
(1937, 1, 'http://www.linguistica.unb.br'),
(1938, 1, 'http://www.matematica.unb.br'),
(1939, 1, 'http://www.musica.unb.br'),
(1940, 1, 'http://www.nutricao.unb.br'),
(1941, 1, 'http://www.ondontologia.unb.br'),
(1942, 1, 'http://www.servicosocial.unb.br'),
(1943, 1, 'http://www.adm.unb.br'),
(1944, 2, 'http://dadosabertos.unb.br/cursos/19'),
(1945, 2, 'http://dadosabertos.unb.br/cursos/27'),
(1946, 2, 'http://dadosabertos.unb.br/cursos/35'),
(1947, 2, 'http://dadosabertos.unb.br/cursos/43'),
(1948, 2, 'http://dadosabertos.unb.br/cursos/51'),
(1949, 2, 'http://dadosabertos.unb.br/cursos/60'),
(1950, 2, 'http://dadosabertos.unb.br/cursos/94'),
(1951, 2, 'http://dadosabertos.unb.br/cursos/124'),
(1952, 2, 'http://dadosabertos.unb.br/cursos/132'),
(1953, 2, 'http://dadosabertos.unb.br/cursos/141'),
(1954, 2, 'http://dadosabertos.unb.br/cursos/159'),
(1955, 2, 'http://dadosabertos.unb.br/cursos/167'),
(1956, 2, 'http://dadosabertos.unb.br/cursos/175'),
(1957, 2, 'http://dadosabertos.unb.br/cursos/183'),
(1958, 2, 'http://dadosabertos.unb.br/cursos/191'),
(1959, 2, 'http://dadosabertos.unb.br/cursos/205'),
(1960, 2, 'http://dadosabertos.unb.br/cursos/213'),
(1961, 2, 'http://dadosabertos.unb.br/cursos/221'),
(1962, 2, 'http://dadosabertos.unb.br/cursos/230'),
(1963, 2, 'http://dadosabertos.unb.br/cursos/264'),
(1964, 2, 'http://dadosabertos.unb.br/cursos/272'),
(1965, 2, 'http://dadosabertos.unb.br/cursos/281'),
(1966, 2, 'http://dadosabertos.unb.br/cursos/299'),
(1967, 2, 'http://dadosabertos.unb.br/cursos/329'),
(1968, 2, 'http://dadosabertos.unb.br/cursos/337'),
(1969, 2, 'http://dadosabertos.unb.br/cursos/345'),
(1970, 2, 'http://dadosabertos.unb.br/cursos/353'),
(1971, 2, 'http://dadosabertos.unb.br/cursos/370'),
(1972, 2, 'http://dadosabertos.unb.br/cursos/388'),
(1973, 2, 'http://dadosabertos.unb.br/cursos/396'),
(1974, 2, 'http://dadosabertos.unb.br/cursos/400'),
(1975, 2, 'http://dadosabertos.unb.br/cursos/442'),
(1976, 2, 'http://dadosabertos.unb.br/cursos/451'),
(1977, 2, 'http://dadosabertos.unb.br/cursos/507'),
(1978, 2, 'http://dadosabertos.unb.br/cursos/582'),
(1979, 2, 'http://dadosabertos.unb.br/cursos/591'),
(1980, 2, 'http://dadosabertos.unb.br/cursos/604'),
(1981, 2, 'http://dadosabertos.unb.br/cursos/639'),
(1982, 2, 'http://dadosabertos.unb.br/cursos/647'),
(1983, 2, 'http://dadosabertos.unb.br/cursos/671'),
(1984, 2, 'http://dadosabertos.unb.br/cursos/680'),
(1985, 2, 'http://dadosabertos.unb.br/cursos/698'),
(1986, 2, 'http://dadosabertos.unb.br/cursos/701'),
(1987, 2, 'http://dadosabertos.unb.br/cursos/710'),
(1988, 2, 'http://dadosabertos.unb.br/cursos/728'),
(1989, 2, 'http://dadosabertos.unb.br/cursos/736'),
(1990, 2, 'http://dadosabertos.unb.br/cursos/744'),
(1991, 2, 'http://dadosabertos.unb.br/cursos/752'),
(1992, 2, 'http://dadosabertos.unb.br/cursos/761'),
(1993, 2, 'http://dadosabertos.unb.br/cursos/779'),
(1994, 2, 'http://dadosabertos.unb.br/cursos/787'),
(1995, 2, 'http://dadosabertos.unb.br/cursos/809'),
(1996, 2, 'http://dadosabertos.unb.br/cursos/817'),
(1997, 2, 'http://dadosabertos.unb.br/cursos/825'),
(1998, 2, 'http://dadosabertos.unb.br/cursos/876'),
(1999, 2, 'http://dadosabertos.unb.br/cursos/884'),
(2000, 2, 'http://dadosabertos.unb.br/cursos/892'),
(2001, 2, 'http://dadosabertos.unb.br/cursos/906'),
(2002, 2, 'http://dadosabertos.unb.br/cursos/914'),
(2003, 2, 'http://dadosabertos.unb.br/cursos/949'),
(2004, 2, 'http://dadosabertos.unb.br/cursos/1023'),
(2005, 2, 'http://dadosabertos.unb.br/cursos/1091'),
(2006, 2, 'http://dadosabertos.unb.br/cursos/1104'),
(2007, 2, 'http://dadosabertos.unb.br/cursos/1112'),
(2008, 2, 'http://dadosabertos.unb.br/cursos/1121'),
(2009, 2, 'http://dadosabertos.unb.br/cursos/1139'),
(2010, 2, 'http://dadosabertos.unb.br/cursos/1147'),
(2011, 2, 'http://dadosabertos.unb.br/cursos/1155'),
(2012, 2, 'http://dadosabertos.unb.br/cursos/1163'),
(2013, 2, 'http://dadosabertos.unb.br/cursos/1171'),
(2014, 2, 'http://dadosabertos.unb.br/cursos/1180'),
(2015, 2, 'http://dadosabertos.unb.br/cursos/1228'),
(2016, 2, 'http://dadosabertos.unb.br/cursos/1236'),
(2017, 2, 'http://dadosabertos.unb.br/cursos/1244'),
(2018, 2, 'http://dadosabertos.unb.br/cursos/1252'),
(2019, 2, 'http://dadosabertos.unb.br/cursos/1261'),
(2020, 2, 'http://dadosabertos.unb.br/cursos/1279'),
(2021, 2, 'http://dadosabertos.unb.br/cursos/1287'),
(2022, 2, 'http://dadosabertos.unb.br/cursos/1295'),
(2023, 2, 'http://dadosabertos.unb.br/cursos/1309'),
(2024, 2, 'http://dadosabertos.unb.br/cursos/1317'),
(2025, 2, 'http://dadosabertos.unb.br/cursos/1325'),
(2026, 2, 'http://dadosabertos.unb.br/cursos/1333'),
(2027, 2, 'http://dadosabertos.unb.br/cursos/1341'),
(2028, 2, 'http://dadosabertos.unb.br/cursos/1350'),
(2029, 2, 'http://dadosabertos.unb.br/cursos/1368'),
(2030, 2, 'http://dadosabertos.unb.br/cursos/1376'),
(2031, 2, 'http://dadosabertos.unb.br/cursos/1384'),
(2032, 2, 'http://dadosabertos.unb.br/cursos/1392'),
(2033, 2, 'http://dadosabertos.unb.br/cursos/1406'),
(2034, 2, 'http://dadosabertos.unb.br/cursos/1414'),
(2035, 2, 'http://dadosabertos.unb.br/cursos/1422'),
(2036, 2, 'http://dadosabertos.unb.br/cursos/1431'),
(2037, 2, 'http://dadosabertos.unb.br/cursos/1449'),
(2038, 2, 'http://dadosabertos.unb.br/cursos/1457'),
(2039, 2, 'http://dadosabertos.unb.br/cursos/1465'),
(2040, 2, 'http://dadosabertos.unb.br/cursos/1473'),
(2041, 2, 'http://dadosabertos.unb.br/cursos/1481'),
(2042, 2, 'http://dadosabertos.unb.br/cursos/1490'),
(2043, 2, 'http://dadosabertos.unb.br/cursos/1503'),
(2044, 2, 'http://dadosabertos.unb.br/cursos/1511'),
(2045, 2, 'http://dadosabertos.unb.br/cursos/1520'),
(2046, 2, 'http://dadosabertos.unb.br/cursos/1538'),
(2047, 2, 'http://dadosabertos.unb.br/cursos/1546'),
(2048, 2, 'http://dadosabertos.unb.br/cursos/1554'),
(2049, 2, 'http://dadosabertos.unb.br/cursos/1562'),
(2050, 2, 'http://dadosabertos.unb.br/cursos/1571'),
(2051, 2, 'http://dadosabertos.unb.br/cursos/1589'),
(2052, 2, 'http://dadosabertos.unb.br/cursos/1601'),
(2053, 2, 'http://dadosabertos.unb.br/cursos/1619'),
(2054, 2, 'http://dadosabertos.unb.br/cursos/1627'),
(2055, 2, 'http://dadosabertos.unb.br/cursos/1635'),
(2056, 2, 'http://dadosabertos.unb.br/cursos/1643');

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
  `sujeito` varchar(255) NOT NULL,
  `predicado` int(11) NOT NULL,
  `objeto` varchar(511) NOT NULL,
  `id_publicacao` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_tripla`
--

INSERT INTO `tb_tripla` (`id_tripla`, `sujeito`, `predicado`, `objeto`, `id_publicacao`) VALUES
(2, 'http://cdt.unb.br', 13, '52', 1),
(3, 'http://cdt.unb.brhttp://cdt.unb.br', 13, 'CDT', 1),
(4, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.br', 13, 'CENTRO DE APOIO AO DESENVOLVIMENTO TECNOLÓGICO', 1),
(5, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.br', 13, 'http://cdt.unb.br', 1),
(6, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.br', 13, '383', 1),
(7, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'CDS', 1),
(8, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'CENTRO DE DESENVOLVIMENTO SUSTENTÁVEL', 1),
(9, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.br', 13, 'http://cds.unb.br', 1),
(10, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.br', 13, '25', 1),
(11, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'CEAM', 1),
(12, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'CENTRO DE ESTUDOS AVANÇADOS E MULTIDISCIPLINARES', 1),
(13, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.br', 13, 'http://ceam.unb.br', 1),
(14, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://cet.unb.br', 13, '39', 1),
(15, 'http://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cdt.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://cds.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://ceam.unb.brhttp://cet.unb.brhttp://cet.unb.br', 13, 'CET', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_unidade_publicadora`
--

CREATE TABLE `tb_unidade_publicadora` (
  `id_unidade_publicadora` int(11) NOT NULL,
  `nome_unidade_publicadora` varchar(255) NOT NULL,
  `sigla` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_unidade_publicadora`
--

INSERT INTO `tb_unidade_publicadora` (`id_unidade_publicadora`, `nome_unidade_publicadora`, `sigla`) VALUES
(1, 'Universidade de Brasília', 'UnB');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_coluna`
--
ALTER TABLE `tb_coluna`
  ADD PRIMARY KEY (`id_coluna`),
  ADD KEY `dataset` (`id_dataset`),
  ADD KEY `termo` (`id_termo`),
  ADD KEY `tb_coluna_ibfk_1` (`id_coluna_ligacao`);

--
-- Indexes for table `tb_dataset`
--
ALTER TABLE `tb_dataset`
  ADD PRIMARY KEY (`id_dataset`),
  ADD KEY `id_termo_tipo` (`id_termo_tipo`),
  ADD KEY `id_coluna` (`id_termo`),
  ADD KEY `id_instancia_ckan` (`id_instancia_ckan`);

--
-- Indexes for table `tb_instancia_ckan`
--
ALTER TABLE `tb_instancia_ckan`
  ADD PRIMARY KEY (`id_instancia_ckan`),
  ADD KEY `ckan_orgao` (`id_unidade_publicadora`);

--
-- Indexes for table `tb_nota`
--
ALTER TABLE `tb_nota`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_objeto`
--
ALTER TABLE `tb_objeto`
  ADD PRIMARY KEY (`id_objeto`),
  ADD KEY `objeto_sujeito` (`id_sujeito`),
  ADD KEY `objeto_predicado` (`id_termo`),
  ADD KEY `objeto_tipo` (`id_objeto_tipo`),
  ADD KEY `objeto_coluna` (`id_coluna`);

--
-- Indexes for table `tb_objeto_tipo`
--
ALTER TABLE `tb_objeto_tipo`
  ADD PRIMARY KEY (`id_objeto_tipo`);

--
-- Indexes for table `tb_ontologia`
--
ALTER TABLE `tb_ontologia`
  ADD PRIMARY KEY (`id_ontologia`);

--
-- Indexes for table `tb_parametro`
--
ALTER TABLE `tb_parametro`
  ADD PRIMARY KEY (`id_parametro`),
  ADD KEY `dataset_parametro` (`id_dataset`),
  ADD KEY `parametro_tipo` (`id_parametro_tipo`);

--
-- Indexes for table `tb_parametro_tipo`
--
ALTER TABLE `tb_parametro_tipo`
  ADD PRIMARY KEY (`id_parametro_tipo`);

--
-- Indexes for table `tb_publicacao`
--
ALTER TABLE `tb_publicacao`
  ADD UNIQUE KEY `id_publicacao` (`id_publicacao`),
  ADD KEY `id_dataset` (`id_dataset`);

--
-- Indexes for table `tb_sujeito`
--
ALTER TABLE `tb_sujeito`
  ADD PRIMARY KEY (`id_sujeito`),
  ADD KEY `sujeito_publicacao` (`id_publicacao`);

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
  ADD KEY `id_publicacao` (`id_publicacao`);

--
-- Indexes for table `tb_unidade_publicadora`
--
ALTER TABLE `tb_unidade_publicadora`
  ADD PRIMARY KEY (`id_unidade_publicadora`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_coluna`
--
ALTER TABLE `tb_coluna`
  MODIFY `id_coluna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tb_dataset`
--
ALTER TABLE `tb_dataset`
  MODIFY `id_dataset` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_instancia_ckan`
--
ALTER TABLE `tb_instancia_ckan`
  MODIFY `id_instancia_ckan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_nota`
--
ALTER TABLE `tb_nota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tb_objeto`
--
ALTER TABLE `tb_objeto`
  MODIFY `id_objeto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=377;

--
-- AUTO_INCREMENT for table `tb_objeto_tipo`
--
ALTER TABLE `tb_objeto_tipo`
  MODIFY `id_objeto_tipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_ontologia`
--
ALTER TABLE `tb_ontologia`
  MODIFY `id_ontologia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_parametro`
--
ALTER TABLE `tb_parametro`
  MODIFY `id_parametro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tb_parametro_tipo`
--
ALTER TABLE `tb_parametro_tipo`
  MODIFY `id_parametro_tipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_publicacao`
--
ALTER TABLE `tb_publicacao`
  MODIFY `id_publicacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_sujeito`
--
ALTER TABLE `tb_sujeito`
  MODIFY `id_sujeito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2057;

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
-- AUTO_INCREMENT for table `tb_unidade_publicadora`
--
ALTER TABLE `tb_unidade_publicadora`
  MODIFY `id_unidade_publicadora` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tb_coluna`
--
ALTER TABLE `tb_coluna`
  ADD CONSTRAINT `dataset` FOREIGN KEY (`id_dataset`) REFERENCES `tb_dataset` (`id_dataset`),
  ADD CONSTRAINT `tb_coluna_ibfk_1` FOREIGN KEY (`id_coluna_ligacao`) REFERENCES `tb_coluna` (`id_coluna`),
  ADD CONSTRAINT `termo` FOREIGN KEY (`id_termo`) REFERENCES `tb_termo` (`id_termo`);

--
-- Limitadores para a tabela `tb_dataset`
--
ALTER TABLE `tb_dataset`
  ADD CONSTRAINT `tb_dataset_ibfk_1` FOREIGN KEY (`id_termo_tipo`) REFERENCES `tb_termo` (`id_termo`),
  ADD CONSTRAINT `tb_dataset_ibfk_2` FOREIGN KEY (`id_termo`) REFERENCES `tb_termo` (`id_termo`),
  ADD CONSTRAINT `tb_dataset_ibfk_3` FOREIGN KEY (`id_instancia_ckan`) REFERENCES `tb_instancia_ckan` (`id_instancia_ckan`);

--
-- Limitadores para a tabela `tb_instancia_ckan`
--
ALTER TABLE `tb_instancia_ckan`
  ADD CONSTRAINT `ckan_orgao` FOREIGN KEY (`id_unidade_publicadora`) REFERENCES `tb_unidade_publicadora` (`id_unidade_publicadora`);

--
-- Limitadores para a tabela `tb_objeto`
--
ALTER TABLE `tb_objeto`
  ADD CONSTRAINT `objeto_coluna` FOREIGN KEY (`id_coluna`) REFERENCES `tb_coluna` (`id_coluna`),
  ADD CONSTRAINT `objeto_predicado` FOREIGN KEY (`id_termo`) REFERENCES `tb_termo` (`id_termo`),
  ADD CONSTRAINT `objeto_sujeito` FOREIGN KEY (`id_sujeito`) REFERENCES `tb_sujeito` (`id_sujeito`),
  ADD CONSTRAINT `objeto_tipo` FOREIGN KEY (`id_objeto_tipo`) REFERENCES `tb_objeto_tipo` (`id_objeto_tipo`);

--
-- Limitadores para a tabela `tb_parametro`
--
ALTER TABLE `tb_parametro`
  ADD CONSTRAINT `dataset_parametro` FOREIGN KEY (`id_dataset`) REFERENCES `tb_dataset` (`id_dataset`),
  ADD CONSTRAINT `parametro_tipo` FOREIGN KEY (`id_parametro_tipo`) REFERENCES `tb_parametro_tipo` (`id_parametro_tipo`);

--
-- Limitadores para a tabela `tb_publicacao`
--
ALTER TABLE `tb_publicacao`
  ADD CONSTRAINT `tb_publicacao_ibfk_1` FOREIGN KEY (`id_dataset`) REFERENCES `tb_dataset` (`id_dataset`);

--
-- Limitadores para a tabela `tb_sujeito`
--
ALTER TABLE `tb_sujeito`
  ADD CONSTRAINT `sujeito_publicacao` FOREIGN KEY (`id_publicacao`) REFERENCES `tb_publicacao` (`id_publicacao`);

--
-- Limitadores para a tabela `tb_termo`
--
ALTER TABLE `tb_termo`
  ADD CONSTRAINT `tb_termo_ibfk_1` FOREIGN KEY (`id_ontologia`) REFERENCES `tb_ontologia` (`id_ontologia`);

--
-- Limitadores para a tabela `tb_tripla`
--
ALTER TABLE `tb_tripla`
  ADD CONSTRAINT `predicado` FOREIGN KEY (`predicado`) REFERENCES `tb_termo` (`id_termo`),
  ADD CONSTRAINT `tb_tripla_ibfk_1` FOREIGN KEY (`id_publicacao`) REFERENCES `tb_publicacao` (`id_publicacao`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
