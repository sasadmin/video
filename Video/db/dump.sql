--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-05-18 21:29:24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 183 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 183
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 16402)
-- Name: video_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_categories (
    id integer NOT NULL,
    name character varying(160)
);


ALTER TABLE video_categories OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16400)
-- Name: video_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE video_categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE video_categories_id_seq OWNER TO postgres;

--
-- TOC entry 2042 (class 0 OID 0)
-- Dependencies: 174
-- Name: video_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE video_categories_id_seq OWNED BY video_categories.id;


--
-- TOC entry 179 (class 1259 OID 16414)
-- Name: video_items; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_items (
    id integer NOT NULL,
    ref_title integer,
    midia smallint,
    state smallint
);


ALTER TABLE video_items OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 16412)
-- Name: video_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE video_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE video_items_id_seq OWNER TO postgres;

--
-- TOC entry 2043 (class 0 OID 0)
-- Dependencies: 178
-- Name: video_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE video_items_id_seq OWNED BY video_items.id;


--
-- TOC entry 182 (class 1259 OID 16424)
-- Name: video_renting_items; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_renting_items (
    ref_renting integer,
    ref_item integer,
    dt_due date,
    dt_return date
);


ALTER TABLE video_renting_items OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16420)
-- Name: video_rentings; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_rentings (
    id integer NOT NULL,
    ref_user integer,
    state smallint,
    cost double precision,
    dt_rent date,
    current_payment double precision,
    ref_operator integer
);


ALTER TABLE video_rentings OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 16418)
-- Name: video_rentings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE video_rentings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE video_rentings_id_seq OWNER TO postgres;

--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 180
-- Name: video_rentings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE video_rentings_id_seq OWNED BY video_rentings.id;


--
-- TOC entry 177 (class 1259 OID 16408)
-- Name: video_titles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_titles (
    id integer NOT NULL,
    name character varying(160),
    dt_released date,
    ref_category integer
);


ALTER TABLE video_titles OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 16406)
-- Name: video_titles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE video_titles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE video_titles_id_seq OWNER TO postgres;

--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 176
-- Name: video_titles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE video_titles_id_seq OWNED BY video_titles.id;


--
-- TOC entry 173 (class 1259 OID 16396)
-- Name: video_users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video_users (
    id integer NOT NULL,
    login character varying(80),
    password character varying(80),
    type smallint,
    state smallint,
    dt_created date,
    rg character varying(20),
    name character varying(160)
);


ALTER TABLE video_users OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16394)
-- Name: video_users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE video_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE video_users_id_seq OWNER TO postgres;

--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 172
-- Name: video_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE video_users_id_seq OWNED BY video_users.id;


--
-- TOC entry 1910 (class 2604 OID 16405)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video_categories ALTER COLUMN id SET DEFAULT nextval('video_categories_id_seq'::regclass);


--
-- TOC entry 1912 (class 2604 OID 16417)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video_items ALTER COLUMN id SET DEFAULT nextval('video_items_id_seq'::regclass);


--
-- TOC entry 1913 (class 2604 OID 16423)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video_rentings ALTER COLUMN id SET DEFAULT nextval('video_rentings_id_seq'::regclass);


--
-- TOC entry 1911 (class 2604 OID 16411)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video_titles ALTER COLUMN id SET DEFAULT nextval('video_titles_id_seq'::regclass);


--
-- TOC entry 1909 (class 2604 OID 16399)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video_users ALTER COLUMN id SET DEFAULT nextval('video_users_id_seq'::regclass);


--
-- TOC entry 2026 (class 0 OID 16402)
-- Dependencies: 175
-- Data for Name: video_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_categories (id, name) FROM stdin;
1	Comédia
2	Terror
3	Ação
\.


--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 174
-- Name: video_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('video_categories_id_seq', 3, true);


--
-- TOC entry 2030 (class 0 OID 16414)
-- Dependencies: 179
-- Data for Name: video_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_items (id, ref_title, midia, state) FROM stdin;
3	3	0	0
8	2	0	1
1	1	1	1
2	3	1	0
7	3	0	0
\.


--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 178
-- Name: video_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('video_items_id_seq', 8, true);


--
-- TOC entry 2033 (class 0 OID 16424)
-- Dependencies: 182
-- Data for Name: video_renting_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_renting_items (ref_renting, ref_item, dt_due, dt_return) FROM stdin;
2	2	\N	2015-05-18
2	7	\N	2015-05-18
3	8	2015-05-15	\N
3	1	2015-05-26	\N
\.


--
-- TOC entry 2032 (class 0 OID 16420)
-- Dependencies: 181
-- Data for Name: video_rentings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_rentings (id, ref_user, state, cost, dt_rent, current_payment, ref_operator) FROM stdin;
2	3	1	10	2015-05-18	0	1
3	3	2	10	2015-05-18	0	1
\.


--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 180
-- Name: video_rentings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('video_rentings_id_seq', 3, true);


--
-- TOC entry 2028 (class 0 OID 16408)
-- Dependencies: 177
-- Data for Name: video_titles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_titles (id, name, dt_released, ref_category) FROM stdin;
1	Vingadores	2015-05-18	2
2	Batman	2015-05-11	2
3	Harry Potter	2015-04-27	3
\.


--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 176
-- Name: video_titles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('video_titles_id_seq', 3, true);


--
-- TOC entry 2024 (class 0 OID 16396)
-- Dependencies: 173
-- Data for Name: video_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video_users (id, login, password, type, state, dt_created, rg, name) FROM stdin;
1	admin	admin	2	0	2015-05-17	9999999999	Administrador
2	func	func	1	0	2015-05-18	8888888888	Funcionário
3	client	client	0	0	2015-05-18	7777777777	Cliente
\.


--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 172
-- Name: video_users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('video_users_id_seq', 3, true);


--
-- TOC entry 2040 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-05-18 21:29:25

--
-- PostgreSQL database dump complete
--

