-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 12-02-2025 a las 00:30:19
-- Versión del servidor: 8.4.4
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ProjectBiblio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `LibroFisico`
--

CREATE TABLE `LibroFisico` (
  `id` bigint NOT NULL,
  `codigoInventario` bigint NOT NULL,
  `idLibroGenerico` bigint NOT NULL,
  `estado` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `LibroFisico`
--

INSERT INTO `LibroFisico` (`id`, `codigoInventario`, `idLibroGenerico`, `estado`) VALUES
(1, 8538, 3, 'Prestado'),
(2, 9018, 4, 'Prestado'),
(4, 8794, 4, 'Perdido'),
(5, 3185, 7, 'Prestado'),
(6, 4227, 1, 'En Reparacion'),
(7, 6827, 5, 'Perdido'),
(8, 1953, 4, 'Prestado'),
(9, 4699, 3, 'Prestado'),
(13, 1324, 5, 'Prestado'),
(14, 2132, 10, 'Disponible'),
(15, 2313, 7, 'Disponible');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `LibroGenerico`
--

CREATE TABLE `LibroGenerico` (
  `id` bigint NOT NULL,
  `titulo` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `autor` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `editorial` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `isbn` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `anio` bigint NOT NULL,
  `idTipoLibro` bigint NOT NULL,
  `descripcion` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `LibroGenerico`
--

INSERT INTO `LibroGenerico` (`id`, `titulo`, `autor`, `editorial`, `isbn`, `anio`, `idTipoLibro`, `descripcion`) VALUES
(1, 'La Colmena', 'Tirso de Molina', 'Alfaguara', 'ISBN978-977-56448-527-0', 2019, 2, 'Libro de mist'),
(3, 'El Jarama', 'Federico García Lorca', 'Cambridge', 'ISBN978-598-64864-473-9', 1998, 5, 'Libro de intriga'),
(4, 'Rayuela', 'Carmen Laforet', 'Pearson', 'ISBN978-133-60087-865-4', 1921, 3, 'Libro de historia'),
(5, 'El Quijote', 'Federico García Lorca', 'McGraw Hill', 'ISBN978-67-17495-175-9', 1941, 1, 'Libro de poesía'),
(7, 'La familia de Pascual Duarte', 'Camilo José Cela', 'McGraw Hill', 'ISBN978-192-28834-772-7', 2022, 1, 'Libro de intriga'),
(10, 'La Celestina', 'Carlos Ruiz Zafón', 'Longman', 'ISBN978-747-38843-722-9', 1949, 1, 'Libro de cómic'),
(13, 'El Documental', 'Federico Valverde', 'Pesttt', 'ISBN32423-23423-23432', 2022, 7, 'Libro para leer');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Prestamo`
--

CREATE TABLE `Prestamo` (
  `id` bigint NOT NULL,
  `idUsuario` bigint NOT NULL,
  `idLibroFisico` bigint DEFAULT NULL,
  `fechaPrestamo` date NOT NULL,
  `fechaDevolucion` date NOT NULL,
  `estado` tinyint NOT NULL,
  `opinion` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Prestamo`
--

INSERT INTO `Prestamo` (`id`, `idUsuario`, `idLibroFisico`, `fechaPrestamo`, `fechaDevolucion`, `estado`, `opinion`) VALUES
(1, 1, 8, '2025-02-03', '2025-03-11', 1, NULL),
(2, 1, 2, '2025-02-03', '2025-02-28', 1, NULL),
(11, 5, 4, '2025-02-10', '2025-02-27', 1, NULL),
(12, 1, 9, '2025-02-10', '2025-02-27', 1, NULL),
(13, 2, 5, '2025-02-11', '2025-02-25', 1, NULL),
(14, 1, 13, '2025-02-11', '2025-02-28', 1, NULL),
(15, 1, 1, '2025-02-11', '2025-02-24', 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TipoLibro`
--

CREATE TABLE `TipoLibro` (
  `id` int NOT NULL,
  `genero` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `TipoLibro`
--

INSERT INTO `TipoLibro` (`id`, `genero`) VALUES
(1, 'Misterio'),
(2, 'Narrativo'),
(3, 'Novela'),
(4, 'Terror'),
(5, 'Fantasia'),
(6, 'Romance'),
(7, 'Documenta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TipoUsuario`
--

CREATE TABLE `TipoUsuario` (
  `id` bigint NOT NULL,
  `descripcion` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `TipoUsuario`
--

INSERT INTO `TipoUsuario` (`id`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Contable'),
(3, 'Auditor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `apellido1` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `apellido2` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `idTipoUsuario` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Usuario`
--

INSERT INTO `Usuario` (`id`, `nombre`, `apellido1`, `apellido2`, `email`, `password`, `idTipoUsuario`) VALUES
(1, 'Rosa', 'Perni', 'Gonzalez', 'emailRosa3517@gmail.com', '18e3ee7892fc667aaf23d84e7a8cd46f766543cade9c266235394f7d0574a443', 1),
(2, 'Rafa', 'Rodriguez', 'Fernandez', 'emailRafa2149@gmail.com', '18e3ee7892fc667aaf23d84e7a8cd46f766543cade9c266235394f7d0574a443', 2),
(5, 'Ignacio', 'Sanchez', 'Fernandez', 'emailIgnacio8900@gmail.com', '18e3ee7892fc667aaf23d84e7a8cd46f766543cade9c266235394f7d0574a443', 3),
(19, 'dwada', 'awdwd', 'wadwa', 'awdwadw@cac', 'cecewew', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `LibroFisico`
--
ALTER TABLE `LibroFisico`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `LibroGenerico`
--
ALTER TABLE `LibroGenerico`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `Prestamo`
--
ALTER TABLE `Prestamo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `TipoLibro`
--
ALTER TABLE `TipoLibro`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `TipoUsuario`
--
ALTER TABLE `TipoUsuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `LibroFisico`
--
ALTER TABLE `LibroFisico`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `LibroGenerico`
--
ALTER TABLE `LibroGenerico`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `Prestamo`
--
ALTER TABLE `Prestamo`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `TipoLibro`
--
ALTER TABLE `TipoLibro`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `TipoUsuario`
--
ALTER TABLE `TipoUsuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
