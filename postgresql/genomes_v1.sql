--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: annotation; Type: SCHEMA; Schema: -; Owner: genomes
--

CREATE SCHEMA annotation;


ALTER SCHEMA annotation OWNER TO genomes;

--
-- Name: aux; Type: SCHEMA; Schema: -; Owner: genomes
--

CREATE SCHEMA aux;


ALTER SCHEMA aux OWNER TO genomes;

--
-- Name: sequence; Type: SCHEMA; Schema: -; Owner: genomes
--

CREATE SCHEMA sequence;


ALTER SCHEMA sequence OWNER TO genomes;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = annotation, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: dna_te_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE dna_te_repeats (
    repeat_id integer NOT NULL,
    tsd_sequence character varying(15) DEFAULT NULL::character varying,
    tir_x integer,
    tir_y integer,
    trans_presence boolean,
    trans_sequence text
);


ALTER TABLE dna_te_repeats OWNER TO genomes;

--
-- Name: exons_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE exons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE exons_id_seq OWNER TO genomes;

--
-- Name: exons; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE exons (
    id integer DEFAULT nextval('exons_id_seq'::regclass) NOT NULL,
    name character varying(45) NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) DEFAULT '+'::bpchar NOT NULL,
    mrna_id integer NOT NULL,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL
);


ALTER TABLE exons OWNER TO genomes;

--
-- Name: genes_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE genes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE genes_id_seq OWNER TO genomes;

--
-- Name: genes; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE genes (
    id integer DEFAULT nextval('genes_id_seq'::regclass) NOT NULL,
    name character varying(45) NOT NULL,
    type character varying(10) DEFAULT NULL::character varying,
    sequence_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) DEFAULT '+'::bpchar NOT NULL,
    gc_content numeric(5,2) DEFAULT NULL::numeric,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL
);


ALTER TABLE genes OWNER TO genomes;

--
-- Name: helitron_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE helitron_repeats (
    repeat_id integer NOT NULL,
    potential_cds_count integer,
    orf_greater_than_50aa integer,
    is_potentially_autonomus boolean,
    end_3 character varying(55) DEFAULT NULL::character varying,
    end_5 character varying(55) DEFAULT NULL::character varying,
    is_present_in_sativa boolean
);


ALTER TABLE helitron_repeats OWNER TO genomes;

--
-- Name: line_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE line_repeats (
    repeat_id integer NOT NULL,
    overall_structure_desc character varying(45) DEFAULT NULL::character varying,
    poly_a boolean,
    rt_presence boolean,
    rt_sequence character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE line_repeats OWNER TO genomes;

--
-- Name: ltr_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE ltr_repeats (
    repeat_id integer NOT NULL,
    ltr_5_length integer,
    ltr_3_length integer,
    is_complete boolean,
    is_solo boolean,
    pbs_x integer,
    pbs_y integer,
    ppt_x integer,
    ppt_y integer,
    rt_presence boolean,
    rt_sequence character varying(255) DEFAULT NULL::character varying,
    int_presence boolean,
    int_sequence character varying(255) DEFAULT NULL::character varying,
    ltr_comparison_similarity numeric(5,2) DEFAULT NULL::numeric,
    ltr_comparison_nuc_distance numeric(5,2) DEFAULT NULL::numeric,
    ltr_comparison_mutation_ca integer,
    ltr_comparison_mutation_ct integer,
    ltr_comparison_mutation_at integer,
    ltr_comparison_mutation_cg integer,
    ltr_comparison_insertion_time numeric(4,2) DEFAULT NULL::numeric,
    tsd_sequence character varying(45) DEFAULT NULL::character varying,
    gc_content_5 numeric(5,2) DEFAULT NULL::numeric,
    gc_content_3 numeric(5,2) DEFAULT NULL::numeric,
    rt_stop_codons_count integer,
    int_stop_codons_count integer,
    presence_in_sativa character varying(45) DEFAULT NULL::character varying
);


ALTER TABLE ltr_repeats OWNER TO genomes;

--
-- Name: mite_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE mite_repeats (
    repeat_id integer NOT NULL,
    tsd_seq character varying(3) DEFAULT NULL::character varying,
    tir_x integer,
    tir_y integer
);


ALTER TABLE mite_repeats OWNER TO genomes;

--
-- Name: mrnas; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE mrnas (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) NOT NULL,
    description character varying(55),
    gene_id integer NOT NULL,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL
);


ALTER TABLE mrnas OWNER TO genomes;

--
-- Name: mrnas_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE mrnas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mrnas_id_seq OWNER TO genomes;

--
-- Name: mrnas_id_seq; Type: SEQUENCE OWNED BY; Schema: annotation; Owner: genomes
--

ALTER SEQUENCE mrnas_id_seq OWNED BY mrnas.id;


--
-- Name: repeats_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE repeats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE repeats_id_seq OWNER TO genomes;

--
-- Name: repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE repeats (
    id integer DEFAULT nextval('repeats_id_seq'::regclass) NOT NULL,
    type character varying(10) DEFAULT '--'::character varying NOT NULL,
    repeats_classification_id integer
    sequence_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) DEFAULT '+'::bpchar NOT NULL,
    parent_repeat_id integer,
    similar_repeat_id integer,
    gc_content numeric(5,2) DEFAULT NULL::numeric,
    contained_elements_count integer DEFAULT 0,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL,
    notes text,
    repeat_text text,
);


ALTER TABLE repeats OWNER TO genomes;

--
-- Name: repeats_classification_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE repeats_classification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE repeats_classification_id_seq OWNER TO genomes;

--
-- Name: repeats_classification; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE repeats_classification (
    id integer DEFAULT nextval('repeats_classification_id_seq'::regclass) NOT NULL
    rclass character varying(25) NOT NULL,
    subclass character varying(25) NOT NULL,
    rorder character varying(45) NOT NULL,
    superfamily character varying(45) NOT NULL,
    family character varying(45) NOT NULL,
);


ALTER TABLE repeats_classification OWNER TO genomes;

--
-- Name: rnas; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE rnas (
    id integer NOT NULL,
    name character varying(55) NOT NULL,
    type character varying(25) NOT NULL,
    rna_name character varying(55),
    sequence_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) DEFAULT '+'::bpchar NOT NULL,
    gc_content numeric(5,2),
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp with time zone NOT NULL,
    parent_rna_id integer
);


ALTER TABLE rnas OWNER TO genomes;

--
-- Name: rnas_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE rnas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rnas_id_seq OWNER TO genomes;

--
-- Name: rnas_id_seq; Type: SEQUENCE OWNED BY; Schema: annotation; Owner: genomes
--

ALTER SEQUENCE rnas_id_seq OWNED BY rnas.id;


--
-- Name: sine_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE sine_repeats (
    repeat_id integer NOT NULL,
    overall_structure_desc character varying(45) DEFAULT NULL::character varying
);


ALTER TABLE sine_repeats OWNER TO genomes;

--
-- Name: snps; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE snps (
    id integer NOT NULL,
    sequence_id integer NOT NULL,
    pos integer NOT NULL,
    reference character(1) NOT NULL,
    reseq character(1) NOT NULL,
    individual_id integer NOT NULL
);


ALTER TABLE snps OWNER TO genomes;

--
-- Name: snps_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE snps_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE snps_id_seq OWNER TO genomes;

--
-- Name: snps_id_seq; Type: SEQUENCE OWNED BY; Schema: annotation; Owner: genomes
--

ALTER SEQUENCE snps_id_seq OWNED BY snps.id;


--
-- Name: unknown_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE unknown_repeats (
    repeat_id integer NOT NULL,
    description character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE unknown_repeats OWNER TO genomes;

SET search_path = aux, pg_catalog;

--
-- Name: _sys_config; Type: TABLE; Schema: aux; Owner: genomes; Tablespace: 
--

CREATE TABLE _sys_config (
    category character varying(15) NOT NULL,
    keyword character varying(155) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE _sys_config OWNER TO genomes;

--
-- Name: queries; Type: TABLE; Schema: aux; Owner: genomes; Tablespace: 
--

CREATE TABLE queries (
    id integer NOT NULL,
    query text,
    description text
);


ALTER TABLE queries OWNER TO genomes;

--
-- Name: queries_id_seq; Type: SEQUENCE; Schema: aux; Owner: genomes
--

CREATE SEQUENCE queries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE queries_id_seq OWNER TO genomes;

--
-- Name: queries_id_seq; Type: SEQUENCE OWNED BY; Schema: aux; Owner: genomes
--

ALTER SEQUENCE queries_id_seq OWNED BY queries.id;


--
-- Name: site_blasts_id_seq; Type: SEQUENCE; Schema: aux; Owner: genomes
--

CREATE SEQUENCE site_blasts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE site_blasts_id_seq OWNER TO genomes;

--
-- Name: site_blasts; Type: TABLE; Schema: aux; Owner: genomes; Tablespace: 
--

CREATE TABLE site_blasts (
    id integer DEFAULT nextval('site_blasts_id_seq'::regclass) NOT NULL,
    result text,
    send_results_to text,
    started timestamp without time zone DEFAULT now() NOT NULL,
    ended timestamp without time zone
);


ALTER TABLE site_blasts OWNER TO genomes;

SET search_path = sequence, pg_catalog;

--
-- Name: chromosomes_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE chromosomes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE chromosomes_id_seq OWNER TO genomes;

--
-- Name: chromosomes; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE chromosomes (
    id integer DEFAULT nextval('chromosomes_id_seq'::regclass) NOT NULL,
    number character varying(4) NOT NULL,
    species_id integer
);


ALTER TABLE chromosomes OWNER TO genomes;

--
-- Name: individuals; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE individuals (
    id integer NOT NULL,
    description character varying(255),
    variety_id integer
);


ALTER TABLE individuals OWNER TO genomes;

--
-- Name: individuals_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE individuals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE individuals_id_seq OWNER TO genomes;

--
-- Name: individuals_id_seq; Type: SEQUENCE OWNED BY; Schema: sequence; Owner: genomes
--

ALTER SEQUENCE individuals_id_seq OWNED BY individuals.id;


--
-- Name: pseudomolecules; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE pseudomolecules (
    sequence_id integer NOT NULL,
    is_scaffold_derived boolean DEFAULT false NOT NULL,
    is_unplaced boolean DEFAULT false NOT NULL
);


ALTER TABLE pseudomolecules OWNER TO genomes;

--
-- Name: scaffolds; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE scaffolds (
    sequence_id integer NOT NULL,
    scaff_order integer DEFAULT 0 NOT NULL,
    pseudomolecule_id integer,
    is_unplaced boolean DEFAULT false NOT NULL
);


ALTER TABLE scaffolds OWNER TO genomes;

--
-- Name: sequences_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE sequences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequences_id_seq OWNER TO genomes;

--
-- Name: sequences; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE sequences (
    id integer DEFAULT nextval('sequences_id_seq'::regclass) NOT NULL,
    type character varying(15) DEFAULT '--'::character varying NOT NULL,
    sequence_text text,
    length integer DEFAULT 0 NOT NULL,
    chromosome_id integer NOT NULL,
    superseded_by integer,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL,
    name character varying(55),
    version character varying(9)
);


ALTER TABLE sequences OWNER TO genomes;

--
-- Name: species_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE species_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE species_id_seq OWNER TO genomes;

--
-- Name: species; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE species (
    id integer DEFAULT nextval('species_id_seq'::regclass) NOT NULL
    genus character varying(45) NOT NULL,
    species character varying(45) NOT NULL,
    subspecies character varying(45) NOT NULL,
    common_name character varying(45) DEFAULT NULL::character varying,
    genome_type character varying(15) DEFAULT NULL::character varying,
);


ALTER TABLE species OWNER TO genomes;

--
-- Name: varieties_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE varieties_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE varieties_id_seq OWNER TO genomes;

--
-- Name: varieties; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE varieties (
    id integer DEFAULT nextval('varieties_id_seq'::regclass) NOT NULL
    name character varying(55) NOT NULL,
    alias character varying(55),
    species_id integer,
);


ALTER TABLE varieties OWNER TO genomes;

SET search_path = annotation, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY mrnas ALTER COLUMN id SET DEFAULT nextval('mrnas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY rnas ALTER COLUMN id SET DEFAULT nextval('rnas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY snps ALTER COLUMN id SET DEFAULT nextval('snps_id_seq'::regclass);


SET search_path = aux, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: aux; Owner: genomes
--

ALTER TABLE ONLY queries ALTER COLUMN id SET DEFAULT nextval('queries_id_seq'::regclass);


SET search_path = sequence, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY individuals ALTER COLUMN id SET DEFAULT nextval('individuals_id_seq'::regclass);


SET search_path = annotation, pg_catalog;

--
-- Data for Name: dna_te_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY dna_te_repeats (repeat_id, tsd_sequence, tir_x, tir_y, trans_presence, trans_sequence) FROM stdin;
\.


--
-- Data for Name: exons; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY exons (id, name, x, y, strandness, mrna_id, date_modified, date_created) FROM stdin;
\.


--
-- Name: exons_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('exons_id_seq', 142093, true);


--
-- Data for Name: genes; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY genes (id, name, type, sequence_id, x, y, strandness, gc_content, date_modified, date_created) FROM stdin;
\.


--
-- Name: genes_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('genes_id_seq', 33196, true);


--
-- Data for Name: helitron_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY helitron_repeats (repeat_id, potential_cds_count, orf_greater_than_50aa, is_potentially_autonomus, end_3, end_5, is_present_in_sativa) FROM stdin;
\.


--
-- Data for Name: line_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY line_repeats (repeat_id, overall_structure_desc, poly_a, rt_presence, rt_sequence) FROM stdin;
\.


--
-- Data for Name: ltr_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY ltr_repeats (repeat_id, ltr_5_length, ltr_3_length, is_complete, is_solo, pbs_x, pbs_y, ppt_x, ppt_y, rt_presence, rt_sequence, int_presence, int_sequence, ltr_comparison_similarity, ltr_comparison_nuc_distance, ltr_comparison_mutation_ca, ltr_comparison_mutation_ct, ltr_comparison_mutation_at, ltr_comparison_mutation_cg, ltr_comparison_insertion_time, tsd_sequence, gc_content_5, gc_content_3, rt_stop_codons_count, int_stop_codons_count, presence_in_sativa) FROM stdin;
\.


--
-- Data for Name: mite_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY mite_repeats (repeat_id, tsd_seq, tir_x, tir_y) FROM stdin;
\.


--
-- Data for Name: mrnas; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY mrnas (id, name, x, y, strandness, description, gene_id, date_modified, date_created) FROM stdin;
\.


--
-- Name: mrnas_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('mrnas_id_seq', 33172, true);


--
-- Data for Name: repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY repeats (id, type, sequence_id, x, y, strandness, parent_repeat_id, similar_repeat_id, gc_content, contained_elements_count, date_modified, date_created, notes, repeat_text, repeats_classification_id) FROM stdin;
\.


--
-- Data for Name: repeats_classification; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY repeats_classification (rclass, subclass, rorder, superfamily, family, id) FROM stdin;
I	I	LINE	NA	NA	1
I	I	LTR	Copia	ATCOPIA17I	2
I	I	LTR	Copia	ATCOPIA24I	3
I	I	LTR	Copia	ATCOPIA57	4
I	I	LTR	Copia	ATCOPIA59	5
I	I	LTR	Copia	ATCOPIA64	6
I	I	LTR	Copia	ATCOPIA77	7
I	I	LTR	Copia	COP1	8
I	I	LTR	Copia	COP18	9
I	I	LTR	Copia	COPI1	10
I	I	LTR	Copia	COPI3	11
I	I	LTR	Copia	COPIA-1	12
I	I	LTR	Copia	COPIA-10	13
I	I	LTR	Copia	COPIA-101	14
I	I	LTR	Copia	COPIA-103	15
I	I	LTR	Copia	COPIA-104	16
I	I	LTR	Copia	COPIA-11	17
I	I	LTR	Copia	COPIA-12	18
I	I	LTR	Copia	COPIA-120	19
I	I	LTR	Copia	COPIA-129	20
I	I	LTR	Copia	COPIA-13	21
I	I	LTR	Copia	COPIA-132	22
I	I	LTR	Copia	COPIA-133	23
I	I	LTR	Copia	COPIA-139	24
I	I	LTR	Copia	COPIA-15	25
I	I	LTR	Copia	COPIA-16	26
I	I	LTR	Copia	COPIA-18	27
I	I	LTR	Copia	COPIA-19	28
I	I	LTR	Copia	COPIA-23	29
I	I	LTR	Copia	COPIA-24	30
I	I	LTR	Copia	COPIA-28	31
I	I	LTR	Copia	COPIA-30	32
I	I	LTR	Copia	COPIA-31	33
I	I	LTR	Copia	COPIA-35	34
I	I	LTR	Copia	COPIA-36	35
I	I	LTR	Copia	COPIA-37	36
I	I	LTR	Copia	COPIA-38	37
I	I	LTR	Copia	COPIA-39	38
I	I	LTR	Copia	COPIA-40	39
I	I	LTR	Copia	COPIA-42	40
I	I	LTR	Copia	COPIA-43	41
I	I	LTR	Copia	COPIA-44	42
I	I	LTR	Copia	COPIA-45	43
I	I	LTR	Copia	COPIA-46	44
I	I	LTR	Copia	COPIA-47	45
I	I	LTR	Copia	COPIA-50	46
I	I	LTR	Copia	COPIA-55	47
I	I	LTR	Copia	COPIA-58	48
I	I	LTR	Copia	COPIA-59	49
I	I	LTR	Copia	COPIA-60	50
I	I	LTR	Copia	COPIA-64	51
I	I	LTR	Copia	COPIA-67	52
I	I	LTR	Copia	COPIA-68	53
I	I	LTR	Copia	COPIA-7	54
I	I	LTR	Copia	COPIA-70	55
I	I	LTR	Copia	COPIA-72	56
I	I	LTR	Copia	COPIA-73	57
I	I	LTR	Copia	COPIA-75	58
I	I	LTR	Copia	COPIA-77	59
I	I	LTR	Copia	COPIA-78	60
I	I	LTR	Copia	COPIA-8	61
I	I	LTR	Copia	COPIA-82	62
I	I	LTR	Copia	COPIA-84	63
I	I	LTR	Copia	COPIA-86	64
I	I	LTR	Copia	COPIA-93	65
I	I	LTR	Copia	COPIA-95	66
I	I	LTR	Copia	COPIA-96	67
I	I	LTR	Copia	COPIA/A	68
I	I	LTR	Copia	COPIA/ACGS	69
I	I	LTR	Copia	COPIA/B	70
I	I	LTR	Copia	COPIA/BCGS	71
I	I	LTR	Copia	COPIA/C	72
I	I	LTR	Copia	COPIA/CCGS	73
I	I	LTR	Copia	COPIA/D	74
I	I	LTR	Copia	COPIA/DCGS	75
I	I	LTR	Copia	COPIA/E	76
I	I	LTR	Copia	COPIA/ECGS	77
I	I	LTR	Copia	COPIA/F	78
I	I	LTR	Copia	COPIA/G	79
I	I	LTR	Copia	COPIA/GCGS	80
I	I	LTR	Copia	COPIA/H	81
I	I	LTR	Copia	COPIA/I	82
I	I	LTR	Copia	COPIA/ICGS	83
I	I	LTR	Copia	COPIA/JCGS	84
I	I	LTR	Copia	COPIA/KCGS	85
I	I	LTR	Copia	COPIA/L	86
I	I	LTR	Copia	COPIA/LCGS	87
I	I	LTR	Copia	COPIA/NCGS	88
I	I	LTR	Copia	COPIA/OCGS	89
I	I	LTR	Copia	COPIA/PCGS	90
I	I	LTR	Copia	COPIA/QCGS	91
I	I	LTR	Copia	COPIA/RCGS	92
I	I	LTR	Copia	COPIA/TCGS	93
I	I	LTR	Copia	COPIA1	94
I	I	LTR	Copia	COPIA10	95
I	I	LTR	Copia	COPIA11	96
I	I	LTR	Copia	COPIA12	97
I	I	LTR	Copia	COPIA15	98
I	I	LTR	Copia	COPIA16	99
I	I	LTR	Copia	COPIA17	100
I	I	LTR	Copia	COPIA18	101
I	I	LTR	Copia	COPIA19	102
I	I	LTR	Copia	COPIA2	103
I	I	LTR	Copia	COPIA2-I	104
I	I	LTR	Copia	COPIA22	105
I	I	LTR	Copia	COPIA24	106
I	I	LTR	Copia	COPIA29	107
I	I	LTR	Copia	COPIA3	108
I	I	LTR	Copia	COPIA34	109
I	I	LTR	Copia	COPIA4	110
I	I	LTR	Copia	COPIA42	111
I	I	LTR	Copia	COPIA5	112
I	I	LTR	Copia	COPIA6	113
I	I	LTR	Copia	COPIA7	114
I	I	LTR	Copia	COPIA8	115
I	I	LTR	Copia	COPIA9	116
I	I	LTR	Copia	COPIO	117
I	I	LTR	Copia	CPR1	118
I	I	LTR	Copia	CPSC	119
I	I	LTR	Copia	CPSC2	120
I	I	LTR	Copia	CPSC3	121
I	I	LTR	Copia	CPSC4A	122
I	I	LTR	Copia	CPSC4B	123
I	I	LTR	Copia	CPSC5	124
I	I	LTR	Copia	CRM	125
I	I	LTR	Copia	CRMA1	126
I	I	LTR	Copia	HOPSCOTCH	127
I	I	LTR	Copia	OSCOPIA2	128
I	I	LTR	Copia	OSTONOR1	129
I	I	LTR	Copia	OSTONOR2	130
I	I	LTR	Copia	OSTONOR3	131
I	I	LTR	Copia	OSTONOR4	132
I	I	LTR	Copia	OSTONOR5	133
I	I	LTR	Copia	RETROFIT	134
I	I	LTR	Copia	RETROFIT2	135
I	I	LTR	Copia	RETROFIT3	136
I	I	LTR	Copia	RETROFIT4	137
I	I	LTR	Copia	RETROFIT5	138
I	I	LTR	Copia	RETROFIT6	139
I	I	LTR	Copia	RETROFIT7	140
I	I	LTR	Copia	RETROSAT2	141
I	I	LTR	Copia	RIRE1	142
I	I	LTR	Copia	RIRE2	143
I	I	LTR	Copia	RIRE3	144
I	I	LTR	Copia	RIRE5	145
I	I	LTR	Copia	RN107	146
I	I	LTR	Copia	SC-1	147
I	I	LTR	Copia	SC-10	148
I	I	LTR	Copia	SC-3	149
I	I	LTR	Copia	SC-4	150
I	I	LTR	Copia	SC-5	151
I	I	LTR	Copia	SC-6	152
I	I	LTR	Copia	SC-7	153
I	I	LTR	Copia	SC-8	154
I	I	LTR	Copia	SC-9	155
I	I	LTR	Copia	SC9	156
I	I	LTR	Copia	SC9A	157
I	I	LTR	Copia	SZ-10	158
I	I	LTR	Copia	SZ-5	159
I	I	LTR	Copia	SZ-55	160
I	I	LTR	Copia	SZ-6	161
I	I	LTR	Copia	UNCLASSIFIED	162
I	I	LTR	Gypsy	ATHILA	163
I	I	LTR	Gypsy	ATLANTYS	164
I	I	LTR	Gypsy	BAJIE	165
I	I	LTR	Gypsy	BEL	166
I	I	LTR	Gypsy	CENTROMERIC	167
I	I	LTR	Gypsy	CRM	168
I	I	LTR	Gypsy	CRMA	169
I	I	LTR	Gypsy	CRMA1	170
I	I	LTR	Gypsy	GYPSI	171
I	I	LTR	Gypsy	GYPSIA	172
I	I	LTR	Gypsy	GYPSO	173
I	I	LTR	Gypsy	GYPSOR1	174
I	I	LTR	Gypsy	GYPSY	175
I	I	LTR	Gypsy	GYPSY-1	176
I	I	LTR	Gypsy	GYPSY-10	177
I	I	LTR	Gypsy	GYPSY-105	178
I	I	LTR	Gypsy	GYPSY-108	179
I	I	LTR	Gypsy	GYPSY-109	180
I	I	LTR	Gypsy	GYPSY-11	181
I	I	LTR	Gypsy	GYPSY-113	182
I	I	LTR	Gypsy	GYPSY-116	183
I	I	LTR	Gypsy	GYPSY-117	184
I	I	LTR	Gypsy	GYPSY-12	185
I	I	LTR	Gypsy	GYPSY-120	186
I	I	LTR	Gypsy	GYPSY-122	187
I	I	LTR	Gypsy	GYPSY-125	188
I	I	LTR	Gypsy	GYPSY-132	189
I	I	LTR	Gypsy	GYPSY-14	190
I	I	LTR	Gypsy	GYPSY-150	191
I	I	LTR	Gypsy	GYPSY-16	192
I	I	LTR	Gypsy	GYPSY-17	193
I	I	LTR	Gypsy	GYPSY-18	194
I	I	LTR	Gypsy	GYPSY-19	195
I	I	LTR	Gypsy	GYPSY-20	196
I	I	LTR	Gypsy	GYPSY-21	197
I	I	LTR	Gypsy	GYPSY-22	198
I	I	LTR	Gypsy	GYPSY-23	199
I	I	LTR	Gypsy	GYPSY-25	200
I	I	LTR	Gypsy	GYPSY-27	201
I	I	LTR	Gypsy	GYPSY-28	202
I	I	LTR	Gypsy	GYPSY-30	203
I	I	LTR	Gypsy	GYPSY-31	204
I	I	LTR	Gypsy	GYPSY-32	205
I	I	LTR	Gypsy	GYPSY-34	206
I	I	LTR	Gypsy	GYPSY-38	207
I	I	LTR	Gypsy	GYPSY-39	208
I	I	LTR	Gypsy	GYPSY-41	209
I	I	LTR	Gypsy	GYPSY-48	210
I	I	LTR	Gypsy	GYPSY-51	211
I	I	LTR	Gypsy	GYPSY-53	212
I	I	LTR	Gypsy	GYPSY-55	213
I	I	LTR	Gypsy	GYPSY-59	214
I	I	LTR	Gypsy	GYPSY-6	215
I	I	LTR	Gypsy	GYPSY-61	216
I	I	LTR	Gypsy	GYPSY-62	217
I	I	LTR	Gypsy	GYPSY-70	218
I	I	LTR	Gypsy	GYPSY-71	219
I	I	LTR	Gypsy	GYPSY-73	220
I	I	LTR	Gypsy	GYPSY-74	221
I	I	LTR	Gypsy	GYPSY-75	222
I	I	LTR	Gypsy	GYPSY-79	223
I	I	LTR	Gypsy	GYPSY-8	224
I	I	LTR	Gypsy	GYPSY-82	225
I	I	LTR	Gypsy	GYPSY-84	226
I	I	LTR	Gypsy	GYPSY-9	227
I	I	LTR	Gypsy	GYPSY-91	228
I	I	LTR	Gypsy	GYPSY-92	229
I	I	LTR	Gypsy	GYPSY-99	230
I	I	LTR	Gypsy	GYPSY-A	231
I	I	LTR	Gypsy	GYPSY-B	232
I	I	LTR	Gypsy	GYPSY/ACGS	233
I	I	LTR	Gypsy	GYPSY/ATLANTYS	234
I	I	LTR	Gypsy	GYPSY/BAJIE	235
I	I	LTR	Gypsy	GYPSY/BCGS	236
I	I	LTR	Gypsy	GYPSY/C	237
I	I	LTR	Gypsy	GYPSY/CCGS	238
I	I	LTR	Gypsy	GYPSY/ECGS	239
I	I	LTR	Gypsy	GYPSY/FCGS	240
I	I	LTR	Gypsy	GYPSY/GYPSY1	241
I	I	LTR	Gypsy	GYPSY/J	242
I	I	LTR	Gypsy	GYPSY/K	243
I	I	LTR	Gypsy	GYPSY/O	244
I	I	LTR	Gypsy	GYPSY/Q	245
I	I	LTR	Gypsy	GYPSY/RETROSAT2	246
I	I	LTR	Gypsy	GYPSY/RIRE2	247
I	I	LTR	Gypsy	GYPSY/SZ-50	248
I	I	LTR	Gypsy	GYPSY/T	249
I	I	LTR	Gypsy	GYPSY1	250
I	I	LTR	Gypsy	GYPSY1-SB	251
I	I	LTR	Gypsy	GYPSY1-SM	252
I	I	LTR	Gypsy	GYPSY10	253
I	I	LTR	Gypsy	GYPSY11	254
I	I	LTR	Gypsy	GYPSY118	255
I	I	LTR	Gypsy	GYPSY123	256
I	I	LTR	Gypsy	GYPSY14	257
I	I	LTR	Gypsy	GYPSY15	258
I	I	LTR	Gypsy	GYPSY18	259
I	I	LTR	Gypsy	GYPSY19	260
I	I	LTR	Gypsy	GYPSY2	261
I	I	LTR	Gypsy	GYPSY20	262
I	I	LTR	Gypsy	GYPSY23	263
I	I	LTR	Gypsy	GYPSY24	264
I	I	LTR	Gypsy	GYPSY26	265
I	I	LTR	Gypsy	GYPSY29	266
I	I	LTR	Gypsy	GYPSY3	267
I	I	LTR	Gypsy	GYPSY31	268
I	I	LTR	Gypsy	GYPSY33	269
I	I	LTR	Gypsy	GYPSY34	270
I	I	LTR	Gypsy	GYPSY35	271
I	I	LTR	Gypsy	GYPSY36	272
I	I	LTR	Gypsy	GYPSY37	273
I	I	LTR	Gypsy	GYPSY39	274
I	I	LTR	Gypsy	GYPSY4	275
I	I	LTR	Gypsy	GYPSY40	276
I	I	LTR	Gypsy	GYPSY42	277
I	I	LTR	Gypsy	GYPSY46	278
I	I	LTR	Gypsy	GYPSY47	279
I	I	LTR	Gypsy	GYPSY5	280
I	I	LTR	Gypsy	GYPSY52	281
I	I	LTR	Gypsy	GYPSY56	282
I	I	LTR	Gypsy	GYPSY59	283
I	I	LTR	Gypsy	GYPSY6	284
I	I	LTR	Gypsy	GYPSY61	285
I	I	LTR	Gypsy	GYPSY62	286
I	I	LTR	Gypsy	GYPSY65	287
I	I	LTR	Gypsy	GYPSY68	288
I	I	LTR	Gypsy	GYPSY69	289
I	I	LTR	Gypsy	GYPSY7	290
I	I	LTR	Gypsy	GYPSY8	291
I	I	LTR	Gypsy	GYPSY80	292
I	I	LTR	Gypsy	GYPSY82	293
I	I	LTR	Gypsy	GYPSY9	294
I	I	LTR	Gypsy	GYPSY98	295
I	I	LTR	Gypsy	GYPSYA	296
I	I	LTR	Gypsy	GYPSYCHANGED48	297
I	I	LTR	Gypsy	GYPSYO	298
I	I	LTR	Gypsy	GYPSY_1	299
I	I	LTR	Gypsy	GYPSY_2	300
I	I	LTR	Gypsy	GYPSY_29	301
I	I	LTR	Gypsy	GYPSY_3	302
I	I	LTR	Gypsy	GYPSY_5	303
I	I	LTR	Gypsy	GYPSY_6	304
I	I	LTR	Gypsy	GYPSY_7	305
I	I	LTR	Gypsy	GYPSY_8	306
I	I	LTR	Gypsy	GYPSY_9	307
I	I	LTR	Gypsy	LTR/GYPSY/A	308
I	I	LTR	Gypsy	OSR3	309
I	I	LTR	Gypsy	OSR38	310
I	I	LTR	Gypsy	OSR39	311
I	I	LTR	Gypsy	OSR42	312
I	I	LTR	Gypsy	RETRO2	313
I	I	LTR	Gypsy	RETROFIT3	314
I	I	LTR	Gypsy	RETROFIT5	315
I	I	LTR	Gypsy	RETROFIT6	316
I	I	LTR	Gypsy	RETROSAT2	317
I	I	LTR	Gypsy	RETROSAT2A	318
I	I	LTR	Gypsy	RETROSAT3	319
I	I	LTR	Gypsy	RETROSAT4	320
I	I	LTR	Gypsy	RETROSAT5	321
I	I	LTR	Gypsy	RETROSAT6	322
I	I	LTR	Gypsy	RETROSOR2	323
I	I	LTR	Gypsy	RIRE2	324
I	I	LTR	Gypsy	RIRE22	325
I	I	LTR	Gypsy	RIRE3	326
I	I	LTR	Gypsy	RIRE3A	327
I	I	LTR	Gypsy	RIRE7	328
I	I	LTR	Gypsy	RIRE8	329
I	I	LTR	Gypsy	RIRE8A	330
I	I	LTR	Gypsy	RIRE8B	331
I	I	LTR	Gypsy	RIRE8C	332
I	I	LTR	Gypsy	RN	333
I	I	LTR	Gypsy	RN10	334
I	I	LTR	Gypsy	RN107	335
I	I	LTR	Gypsy	RN12	336
I	I	LTR	Gypsy	ROMANI1	337
I	I	LTR	Gypsy	SZ-10	338
I	I	LTR	Gypsy	SZ-22	339
I	I	LTR	Gypsy	SZ-26	340
I	I	LTR	Gypsy	SZ-35	341
I	I	LTR	Gypsy	SZ-39	342
I	I	LTR	Gypsy	SZ-4	343
I	I	LTR	Gypsy	SZ-42	344
I	I	LTR	Gypsy	SZ-43	345
I	I	LTR	Gypsy	SZ-46	346
I	I	LTR	Gypsy	SZ-48	347
I	I	LTR	Gypsy	SZ-49	348
I	I	LTR	Gypsy	SZ-5	349
I	I	LTR	Gypsy	SZ-50	350
I	I	LTR	Gypsy	SZ-52	351
I	I	LTR	Gypsy	SZ-53	352
I	I	LTR	Gypsy	SZ-54A	353
I	I	LTR	Gypsy	SZ-54C	354
I	I	LTR	Gypsy	SZ-54D	355
I	I	LTR	Gypsy	SZ-56	356
I	I	LTR	Gypsy	SZ-56A	357
I	I	LTR	Gypsy	SZ-57	358
I	I	LTR	Gypsy	SZ-61	359
I	I	LTR	Gypsy	SZ-62	360
I	I	LTR	Gypsy	SZ-63	361
I	I	LTR	Gypsy	SZ-64	362
I	I	LTR	Gypsy	SZ-64B	363
I	I	LTR	Gypsy	SZ-66	364
I	I	LTR	Gypsy	SZ-67	365
I	I	LTR	Gypsy	SZ-7	366
I	I	LTR	Gypsy	SZ-7A	367
I	I	LTR	Gypsy	SZ-8	368
I	I	LTR	Gypsy	SZ-9	369
I	I	LTR	Gypsy	SZ-COM	370
I	I	LTR	Gypsy	TRUNCATOR	371
I	I	LTR	Gypsy	UNCLASSIFIED	372
I	I	LTR	Unclassified	BEL	373
I	I	LTR	Unclassified	NA	374
I	I	LTR	Unclassified	SC-1	375
I	I	LTR	Unclassified	SZ	376
I	I	LTR	Unclassified	SZ-10	377
I	I	LTR	Unclassified	SZ-11	378
I	I	LTR	Unclassified	SZ-14	379
I	I	LTR	Unclassified	SZ-16	380
I	I	LTR	Unclassified	SZ-19	381
I	I	LTR	Unclassified	SZ-20	382
I	I	LTR	Unclassified	SZ-21	383
I	I	LTR	Unclassified	SZ-22	384
I	I	LTR	Unclassified	SZ-23	385
I	I	LTR	Unclassified	SZ-24	386
I	I	LTR	Unclassified	SZ-25	387
I	I	LTR	Unclassified	SZ-26	388
I	I	LTR	Unclassified	SZ-27	389
I	I	LTR	Unclassified	SZ-28	390
I	I	LTR	Unclassified	SZ-29	391
I	I	LTR	Unclassified	SZ-30	392
I	I	LTR	Unclassified	SZ-31	393
I	I	LTR	Unclassified	SZ-32	394
I	I	LTR	Unclassified	SZ-33	395
I	I	LTR	Unclassified	SZ-34	396
I	I	LTR	Unclassified	SZ-35	397
I	I	LTR	Unclassified	SZ-36	398
I	I	LTR	Unclassified	SZ-37	399
I	I	LTR	Unclassified	SZ-38	400
I	I	LTR	Unclassified	SZ-39	401
I	I	LTR	Unclassified	SZ-40	402
I	I	LTR	Unclassified	SZ-41	403
I	I	LTR	Unclassified	SZ-42	404
I	I	LTR	Unclassified	SZ-44	405
I	I	LTR	Unclassified	SZ-45	406
I	I	LTR	Unclassified	SZ-47	407
I	I	LTR	Unclassified	SZ-48	408
I	I	LTR	Unclassified	SZ-49	409
I	I	LTR	Unclassified	SZ-5	410
I	I	LTR	Unclassified	SZ-50	411
I	I	LTR	Unclassified	SZ-51	412
I	I	LTR	Unclassified	SZ-52	413
I	I	LTR	Unclassified	SZ-54	414
I	I	LTR	Unclassified	SZ-54D	415
I	I	LTR	Unclassified	SZ-56A	416
I	I	LTR	Unclassified	SZ-57	417
I	I	LTR	Unclassified	SZ-58	418
I	I	LTR	Unclassified	SZ-59	419
I	I	LTR	Unclassified	SZ-6	420
I	I	LTR	Unclassified	SZ-60	421
I	I	LTR	Unclassified	SZ-61	422
I	I	LTR	Unclassified	SZ-62	423
I	I	LTR	Unclassified	SZ-63	424
I	I	LTR	Unclassified	SZ-64	425
I	I	LTR	Unclassified	SZ-64B	426
I	I	LTR	Unclassified	SZ-65	427
I	I	LTR	Unclassified	SZ-66	428
I	I	LTR	Unclassified	SZ-67	429
I	I	LTR	Unclassified	SZ-7	430
I	I	LTR	Unclassified	SZ-7A	431
I	I	LTR	Unclassified	SZ-COM	432
I	I	LTR	Unclassified	TRUNCATOR2	433
I	I	SINE	NA	NA	434
I	I	SINE	NA	OSN1	435
I	I	SINE	NA	OSN2	436
I	I	SINE	NA	OSN3	437
I	I	SINE	NA	P-SINE1	438
I	I	SINE	NA	P-SINE2	439
I	I	SINE	NA	P-SINE3	440
II	I	DNA_TE	CACTA	NA	441
II	I	DNA_TE	EnSPm	NA	442
II	I	DNA_TE	hAT	NA	443
II	I	DNA_TE	Mariner	NA	444
II	I	DNA_TE	Mutator	NA	445
II	I	DNA_TE	NA	NA	446
II	I	DNA_TE	Other	NA	447
II	I	DNA_TE	Other	RMU3	448
II	I	DNA_TE	PIF-Harbinger	NA	449
II	I	DNA_TE	Stowaway	NA	450
II	I	DNA_TE	Tourist	NA	451
II	I	DNA_TE	Unclassified	NA	452
II	II	Helitron	Helitron	NA	453
II	III	MITE	NA	NA	454
UNKNOWN	UNKNOWN	UNKNOWN	UNKNOWN	UNKNOWN	455
II	I	DNA_TE	hAT	JOUZHENA	456
II	I	DNA_TE	Mutator	CLOUD-7	457
II	I	DNA_TE	hAT	HAT_25_SM_A	458
II	I	DNA_TE	Mutator	ATMU13_2P_M	459
II	III	MITE	Tourist	OLO24B	460
II	I	DNA_TE	hAT	HAT_21_SM_1P_C	461
II	III	MITE	Tourist	DITTO	462
II	III	MITE	Tourist	OLO24C	463
II	I	DNA_TE	hAT	HAT_22_SM_C	464
II	I	DNA_TE	hAT	HAT1_AGP_E	465
II	III	MITE	Tourist	OLO24	466
II	I	DNA_TE	hAT	HAT_22_SM_E	467
II	I	DNA_TE	Mutator	ATMU12_U	468
II	III	MITE	Turist	TOURIST6A_OS	469
II	III	MITE	Tourist	CASTAWAY	470
II	I	DNA_TE	Mariner	TNR11	471
II	I	DNA_TE	hAT	ATHAT1P_D	472
II	I	DNA_TE	hAT	ATHAT3P_D	473
II	I	DNA_TE	Mutator	VANDAL20P1_E	474
II	III	MITE	Mutator	MDM2	475
II	III	MITE	Tourist	BUHUI	476
II	I	DNA_TE	hAT	HAT_1_D	477
II	I	DNA_TE	Mutator	VANDAL20P1_G	478
II	I	DNA_TE	Mutator	CLOUD	479
II	I	DNA_TE	hAT	ATHAT10P_C	480
II	I	DNA_TE	hAT	QINNIU	481
II	III	MITE	Mutator	MDR1	482
II	I	DNA_TE	EnSPm	OSHOOTER	483
II	I	DNA_TE	hAT	HAT_20_SM_1P_B	484
II	I	DNA_TE	hAT	HAT_29_SM_A	485
II	III	MITE	Tourist	INDITTO	486
II	I	DNA_TE	Mutator	ATMU11P_A	487
II	I	DNA_TE	hAT	DS-RICE4N	488
II	I	DNA_TE	hAT	HAT_20_SM_1P_A	489
II	I	DNA_TE	hAT	TWIFB1	490
II	III	MITE	Tourist	OLO24A	491
II	I	DNA_TE	Mutator	ATMU11P_I	492
II	I	DNA_TE	hAT	ATHAT2P_C	493
II	III	MITE	Tourist	YOUREN	494
II	I	DNA_TE	hAT	JINHUA	495
II	I	DNA_TE	EnSPm	TNR3	496
II	I	DNA_TE	PIF-Harbinger	NDNA2TNA_OS	497
II	I	DNA_TE	PIF-Harbinger	NDNA1TNA_OS	498
II	I	DNA_TE	hAT	ATHAT1P_A	499
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_A	500
II	I	DNA_TE	Mutator	ATMU4P1_X	501
II	I	DNA_TE	hAT	HAT_22_SM_B	502
II	III	MITE	Tourist	SUSU	503
II	I	DNA_TE	Mutator	MUDRN5_OS	504
II	I	DNA_TE	hAT	F569	505
II	I	DNA_TE	hAT	HAT_21_SM_1P_B	506
II	III	MITE	Tourist	DITAILA	507
II	III	MITE	Tourist	STOLA	508
II	III	MITE	Tourist	STOLAB	509
II	I	DNA_TE	EnSPm	ATENSPM12_1P_H	510
II	I	DNA_TE	Mariner	OSMAR1	511
II	I	DNA_TE	EnSPm	ENSPM-10_OS	512
II	I	DNA_TE	EnSPm	ENSPM5_OS	513
II	I	DNA_TE	Mutator	CLOUD-4	514
II	I	DNA_TE	hAT	DS-RICE3N	515
II	I	DNA_TE	Mutator	AT10MU1_J	516
II	III	MITE	Tourist	QIQI	517
II	I	DNA_TE	PIF-Harbinger	KIDDOC	518
II	I	DNA_TE	Mutator	MUDRN4_OS	519
II	I	DNA_TE	EnSPm	ENSPM-13_OS	520
II	I	DNA_TE	EnSPm	MIDWAY	521
II	I	DNA_TE	EnSPm	ATENSPM12_1P_D	522
II	I	DNA_TE	EnSPm	ENSPM_OS	523
II	I	DNA_TE	EnSPm	ATENSPM12_1P_G	524
II	I	DNA_TE	EnSPm	ENSPM3_OS	525
II	I	DNA_TE	hAT	ATHAT10P_B	526
II	I	DNA_TE	hAT	F118	527
II	I	DNA_TE	EnSPm	ATENSPM7P_D	528
II	I	DNA_TE	Mutator	AT10MU1_M	529
II	I	DNA_TE	Mutator	MUDRN1_OS	530
II	III	MITE	Tourist	ECSR	531
II	I	DNA_TE	hAT	HAT_21_SM_1P_A	532
II	I	DNA_TE	hAT	TESS	533
II	I	DNA_TE	Unclassified	MICROPON-LIKE-1	534
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_C	535
II	III	MITE	Tourist	STONE	536
II	I	DNA_TE	Mutator	ATMU11P_J	537
II	III	MITE	Tourist	COWARD	538
II	I	DNA_TE	hAT	HAT_25_SM_B	539
II	I	DNA_TE	EnSPm	ENSPM-11_OS	540
II	I	DNA_TE	Mutator	AT10MU1_L	541
II	I	DNA_TE	hAT	ATHAT2P_B	542
II	I	DNA_TE	Mutator	CLOUD-3	543
II	I	DNA_TE	EnSPm	ENSPM-N2_OS	544
II	I	DNA_TE	hAT	CRATA-2	545
II	I	DNA_TE	Mutator	MUDRN2_OS	546
II	I	DNA_TE	EnSPm	ENSPM-12_OS	547
II	I	DNA_TE	EnSPm	ATENSPM10P_D	548
II	I	DNA_TE	Mutator	CLOUD-2	549
II	I	DNA_TE	hAT	HAT_24_SM_A	550
II	III	MITE	Tourist	CASLET	551
II	I	DNA_TE	EnSPm	CHESTER_A	552
II	I	DNA_TE	hAT	ATHAT3P_B	553
II	I	DNA_TE	hAT	DS-RICE2N	554
II	III	MITE	Tourist	DITTO3	555
II	I	DNA_TE	EnSPm	ATENSPM10P_E	556
II	I	DNA_TE	Mutator	VANDAL14_1P_I	557
II	I	DNA_TE	Mutator	AT10MU1_E	558
II	I	DNA_TE	Mutator	MUDR3_OS	559
II	I	DNA_TE	Mutator	AT10MU1_C	560
II	I	DNA_TE	Mutator	CLOUD-6	561
II	III	MITE	Tourist	COWARD-3	562
II	III	MITE	Turist	ECR	563
II	III	MITE	Tourist	DITAIL	564
II	I	DNA_TE	Mariner	MARINER_10_A	565
II	I	DNA_TE	PIF-Harbinger	KIDDOD	566
II	I	DNA_TE	hAT	TEMPINDAS	567
II	I	DNA_TE	EnSPm	ATENSPM10P_A	568
II	I	DNA_TE	Mutator	MULEX_OS	569
II	I	DNA_TE	Mutator	MUDR1_OS	570
II	I	DNA_TE	hAT	ATHAT9_C	571
II	I	DNA_TE	hAT	CRATA	572
II	I	DNA_TE	hAT	THRIA	573
II	III	MITE	Turist	WUJI	574
II	I	DNA_TE	Mutator	ATMU11P_L	575
II	I	DNA_TE	PIF-Harbinger	HARB-N1_OS	576
II	I	DNA_TE	hAT	YIREN	577
II	I	DNA_TE	CACTA	CACTA-B	578
II	I	DNA_TE	Mutator	ATMU11P_T	579
II	III	MITE	Tourist	CASGRANDA	580
II	III	MITE	Turist	GLUTEL1LIKE	581
II	I	DNA_TE	EnSPm	ENSPM-15_OS	582
II	I	DNA_TE	hAT	HAT_27_SM_B	583
II	I	DNA_TE	Unclassified	RCH2	584
II	I	DNA_TE	EnSPm	ATENSPM10P_B	585
II	I	DNA_TE	EnSPm	ENSPM4_OS	586
II	I	DNA_TE	CACTA	CACTA-H	587
II	I	DNA_TE	Mutator	ATMU11P_G	588
II	I	DNA_TE	EnSPm	ENSPM2_OS	589
II	I	DNA_TE	Mutator	MUDR4_OS	590
II	I	DNA_TE	EnSPm	ENSPM6_OS	591
II	I	DNA_TE	EnSPm	ATENSPM10P_C	592
II	I	DNA_TE	Mutator	ATMU4P1_V	593
II	I	DNA_TE	hAT	HAT1_AGP_D	594
II	I	DNA_TE	hAT	ATHAT10P_F	595
II	I	DNA_TE	Mutator	MU_OS	596
II	III	MITE	Tourist	NONAME	597
II	I	DNA_TE	hAT	DEBOAT	598
II	I	DNA_TE	Mutator	MUJITOS2	599
II	I	DNA_TE	Mutator	AT10MU1_N	600
II	I	DNA_TE	Mutator	AT10MU1_R	601
II	I	DNA_TE	Mutator	VANDAL20P1_F	602
II	I	DNA_TE	EnSPm	SPMLIKE	603
II	III	MITE	Tourist	HELIA	604
II	I	DNA_TE	hAT	DELAY	605
II	III	MITE	Tourist	TRC1	606
II	I	DNA_TE	CACTA	CACTA-D	607
II	I	DNA_TE	CACTA	CACTA-G1	608
II	I	DNA_TE	Mutator	ATMU11P_C	609
II	I	DNA_TE	EnSPm	ENSPM7_OS	610
II	I	DNA_TE	hAT	HAT2_A	611
II	I	DNA_TE	PIF-Harbinger	KIDDOA	612
II	III	MITE	Mutator	MDR3	613
II	I	DNA_TE	Mutator	ATMU11P_R	614
II	I	DNA_TE	Mutator	ATMU12_Y	615
II	I	DNA_TE	Mutator	ATMU12_X	616
II	I	DNA_TE	Mariner	MARINER_1_C	617
II	I	DNA_TE	EnSPm	ATENSPM10P_G	618
II	I	DNA_TE	Mutator	TNR8-1	619
II	I	DNA_TE	hAT	HAT_22_SM_A	620
II	III	MITE	Mutator	MDR2	621
II	III	MITE	Tourist	F770	622
II	I	DNA_TE	EnSPm	ENSPM-16_OS	623
II	I	DNA_TE	PIF-Harbinger	KIDDOB	624
II	I	DNA_TE	EnSPm	ENSPM-8_OS	625
II	I	DNA_TE	hAT	ATHAT7P_C	626
II	I	DNA_TE	hAT	TESSBIG	627
II	I	DNA_TE	Mutator	AT10MU1_S	628
II	I	DNA_TE	Mutator	ATMU11P_N	629
II	I	DNA_TE	Mutator	ATMU11P_Q	630
II	I	DNA_TE	hAT	ATHAT10P_A	631
II	III	MITE	Tourist	FOCUS	632
II	I	DNA_TE	hAT	HATOS1	633
II	III	MITE	Tourist	CASBIG	634
II	I	DNA_TE	Mutator	ATMU11P_P	635
II	I	DNA_TE	hAT	JOUZHEN	636
II	I	DNA_TE	Mutator	OSMU5	637
II	I	DNA_TE	hAT	HAT_27_SM_A	638
II	I	DNA_TE	Mutator	MUDRN3_OS	639
II	I	DNA_TE	Mutator	VANDAL21P1_R	640
II	I	DNA_TE	Mutator	AT10MU1_H	641
II	I	DNA_TE	Mutator	MUDROS2	642
II	I	DNA_TE	hAT	TAG2P_B	643
II	I	DNA_TE	EnSPm	ENSPM-9_OS	644
II	I	DNA_TE	PIF-Harbinger	ATIS112A_A	645
II	I	DNA_TE	CACTA	CACTA-I	646
II	I	DNA_TE	Mutator	AT10MU1_I	647
II	I	DNA_TE	Mutator	ATMU11P_D	648
II	I	DNA_TE	Mutator	AT10MU1_D	649
II	I	DNA_TE	Mutator	ATMU11P_W	650
II	III	MITE	Turist	HEARTBLEEDING	651
II	I	DNA_TE	Mutator	OSMU6	652
II	I	DNA_TE	Mutator	AT10MU1_F	653
II	I	DNA_TE	Mutator	AT10MU1_O	654
II	I	DNA_TE	hAT	ATHAT7P_F	655
II	I	DNA_TE	Mutator	VANDAL9_1P_H	656
II	I	DNA_TE	Mutator	AT10MU1_B	657
II	I	DNA_TE	Mutator	MUJITOS1	658
II	I	DNA_TE	Mariner	MARINER_1_A	659
II	I	DNA_TE	Mutator	ATMU11P_B	660
II	III	MITE	Tourist	CENTRE	661
II	I	DNA_TE	PIF-Harbinger	ATIS112A_B	662
II	I	DNA_TE	Mutator	AT10MU1_G	663
II	I	DNA_TE	Mutator	AT10MU1_P	664
II	I	DNA_TE	EnSPm	ENSPM-N1_OS	665
II	III	MITE	Mutator	MDM1	666
II	I	DNA_TE	Mutator	OSMUD1	667
II	I	DNA_TE	EnSPm	ATENSPM12_1P_E	668
II	I	DNA_TE	Mutator	MUDROS1	669
II	I	DNA_TE	Mariner	MARINER_1_D	670
II	I	DNA_TE	Mutator	MUDR-5_OS	671
II	I	DNA_TE	Mariner	MARINER_1_B	672
II	I	DNA_TE	hAT	ATHAT10P_D	673
II	I	DNA_TE	Mutator	MUDR2_OS	674
II	I	DNA_TE	EnSPm	ATENSPM7P_A	675
II	I	DNA_TE	Mutator	RMU1A23	676
II	III	MITE	Tourist	CASIN	677
II	I	DNA_TE	Mutator	ATMU4P1_Z	678
II	I	DNA_TE	EnSPm	ENSPM-14_OS	679
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_B	680
II	I	DNA_TE	Mutator	ATMU13_2P_P	681
II	I	DNA_TE	Mutator	AT10MU1_Q	682
II	III	MITE	Tourist	LIER	683
II	III	MITE	Tourist	CASMALL	684
II	I	DNA_TE	Mutator	CLOUD-5	685
II	I	DNA_TE	hAT	TAG2P_E	686
II	III	MITE	Mariner	DTT_OGLA_SA	687
II	III	MITE	Mariner	DTT_OGLA_SW	688
II	III	MITE	Mariner	DTT_OGLA_SH	689
II	III	MITE	Mariner	DTT_OGLA_SQ	690
II	III	MITE	Mariner	DTT_OGLA_SG	691
II	III	MITE	Mariner	DTT_OGLA_SJ	692
II	III	MITE	Mariner	DTT_OGLA_SS	693
II	III	MITE	Mariner	DTT_OGLA_SM	694
II	III	MITE	Mariner	DTT_OGLA_SE	695
II	III	MITE	Mariner	DTT_OGLA_SB	696
II	III	MITE	Mariner	DTT_OGLA_SD	697
II	III	MITE	Mariner	DTT_OGLA_SC	698
II	III	MITE	Mariner	DTT_OGLA_ST	699
II	III	MITE	Mariner	DTT_OGLA_SN	700
II	III	MITE	Mariner	DTT_OGLA_SAC	701
II	III	MITE	Mariner	DTT_OGLA_SV	702
II	III	MITE	Mariner	DTT_OGLA_SK	703
II	III	MITE	Mariner	DTT_OGLA_SU	704
II	III	MITE	Mariner	DTT_OGLA_SAA	705
II	III	MITE	Mariner	DTT_OGLA_SAB	706
II	III	MITE	Mariner	DTT_OGLA_SR	707
II	III	MITE	Mariner	DTT_OGLA_SD-423	708
II	III	MITE	Harbinger	DTH_OGLA_TF	709
II	III	MITE	Harbinger	DTH_OGLA_TC	710
II	III	MITE	Harbinger	DTH_OGLA_TE	711
II	III	MITE	Harbinger	DTH_OGLA_TB	712
II	III	MITE	Harbinger	DTH_OGLA_TA	713
II	III	MITE	Harbinger	DTH_OGLA_TD	714
II	III	MITE	Harbinger	DTH_OGLA_TF-2926	715
\.


--
-- Name: repeats_classification_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('repeats_classification_id_seq', 1052, true);


--
-- Name: repeats_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('repeats_id_seq', 386115, true);


--
-- Data for Name: rnas; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY rnas (id, name, type, rna_name, sequence_id, x, y, strandness, gc_content, date_modified, date_created, parent_rna_id) FROM stdin;
\.


--
-- Name: rnas_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('rnas_id_seq', 849, true);


--
-- Data for Name: sine_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY sine_repeats (repeat_id, overall_structure_desc) FROM stdin;
\.


--
-- Data for Name: snps; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY snps (id, sequence_id, pos, reference, reseq, individual_id) FROM stdin;
\.


--
-- Name: snps_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('snps_id_seq', 32, true);


--
-- Data for Name: unknown_repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY unknown_repeats (repeat_id, description) FROM stdin;
\.


SET search_path = aux, pg_catalog;

--
-- Data for Name: _sys_config; Type: TABLE DATA; Schema: aux; Owner: genomes
--

COPY _sys_config (category, keyword, value) FROM stdin;
repeats	ftp.share	/wing/vsftpd/glabrepeats/repeats_annot
mailer	mail.transport.protocol	smtp
mailer	mail.smtp.host	calsmail.arizona.edu
mailer	sender.default	mbraidot@ag.arizona.edu
\.


--
-- Data for Name: queries; Type: TABLE DATA; Schema: aux; Owner: genomes
--

COPY queries (id, query, description) FROM stdin;
1	select distinct(repeats.id) from repeats join genes on (repeats.sequence_id = genes.sequence_id) join exons on (genes.id = exons.gene_id) where (genes.x <= repeats.x and repeats.y <= genes.y) and ( exons.x >= repeats.y and repeats.y <= exons.y) intersect select distinct(repeats.id) from repeats join genes on (repeats.sequence_id = genes.sequence_id) join exons on (genes.id = exons.gene_id) where (genes.x <= repeats.x and repeats.y <= genes.y) and ( exons.x <= repeats.x and repeats.x >= exons.y);	Searches intronic repeats. Query is not 100% correct, of around 17k results about 100 were wrong. Query is the intersection of two sets, one is the set of repeats which START is not inside any repeat, the second is the set if repeats which END is not inside any repeat.
2	select chromosomes.number, end_3, end_5, is_present_in_sativa, count(repeat_id) from helitron_repeats join repeats on helitron_repeats.repeat_id = repeats.id) join sequences on (repeats.sequence_id = sequences.id) join chromosomes on (sequences.chromosome_id = chromosomes.id) group by chromosomes.number, end_3, end_5, is_present_in_sativa order by chromosomes.number, end_3, end_5;	helitrons presence stats by chromosome
4	select chromosomes.number, ltr_repeats.presence_in_sativa,\ncount(ltr_repeats.repeat_id) from ltr_repeats join repeats on\n(ltr_repeats.repeat_id = repeats.id and is_solo = true) join sequences\non (repeats.sequence_id = sequences.id) join chromosomes on\n(sequences.chromosome_id = chromosomes.id) group by\nchromosomes.number, ltr_repeats.presence_in_sativa order by\nchromosomes.number, ltr_repeats.presence_in_sativa;	solo ltrs presence in sativa stats by chromosome
3	select chromosomes.number, ltr_repeats.presence_in_sativa,\ncount(ltr_repeats.repeat_id) from ltr_repeats join repeats on\n(ltr_repeats.repeat_id = repeats.id and is_complete = true) join\nsequences on (repeats.sequence_id = sequences.id) join chromosomes on\n(sequences.chromosome_id = chromosomes.id) group by\nchromosomes.number, ltr_repeats.presence_in_sativa order by\nchromosomes.number, ltr_repeats.presence_in_sativa;	complete ltrs presence in sativa stats by chromosome
5	select repeats_order, count(*) from repeats join genes on (repeats.sequence_id = genes.sequence_id) left join scaffolds on (repeats.sequence_id = scaffolds.sequence_id) where genes.x <= repeats.x and repeats.y <= genes.y group by repeats_order	genes containing tes group by repeats order
6	select s.id, count(r.id) from repeats r join repeats s on (r.sequence_id = s.sequence_id) where r.id != s.id and r.x >= s.x and r.y <= s.y and r.sequence_id = 321 group by s.id order by s.id	Number of inserted elements
7	select repeats_order, count(*) from repeats join genes on (repeats.sequence_id = genes.sequence_id) where genes.x <= repeats.x and repeats.y <= genes.y group by repeats_order	TEs inside Genes
8	select genes.id,repeats.id, repeats_order from repeats join genes on (repeats.sequence_id = genes.sequence_id) where repeats.x <= genes.x and repeats.y > genes.x OR repeats.y >= genes.y and repeats.x < genes.y OR repeats.x > genes.x and repeats.y < genes.y group by genes.id,repeats.id, repeats_order	TEs Overlap Genes
1	select distinct(repeats.id) from repeats join genes on (repeats.sequence_id = genes.sequence_id) join exons on (genes.id = exons.gene_id) where (genes.x <= repeats.x and repeats.y <= genes.y) and ( exons.x >= repeats.y and repeats.y <= exons.y) intersect select distinct(repeats.id) from repeats join genes on (repeats.sequence_id = genes.sequence_id) join exons on (genes.id = exons.gene_id) where (genes.x <= repeats.x and repeats.y <= genes.y) and ( exons.x <= repeats.x and repeats.x >= exons.y);	Searches intronic repeats. Query is not 100% correct, of around 17k results about 100 were wrong. Query is the intersection of two sets, one is the set of repeats which START is not inside any repeat, the second is the set if repeats which END is not inside any repeat.
2	select chromosomes.number, end_3, end_5, is_present_in_sativa, count(repeat_id) from helitron_repeats join repeats on helitron_repeats.repeat_id = repeats.id) join sequences on (repeats.sequence_id = sequences.id) join chromosomes on (sequences.chromosome_id = chromosomes.id) group by chromosomes.number, end_3, end_5, is_present_in_sativa order by chromosomes.number, end_3, end_5;	helitrons presence stats by chromosome
4	select chromosomes.number, ltr_repeats.presence_in_sativa,\ncount(ltr_repeats.repeat_id) from ltr_repeats join repeats on\n(ltr_repeats.repeat_id = repeats.id and is_solo = true) join sequences\non (repeats.sequence_id = sequences.id) join chromosomes on\n(sequences.chromosome_id = chromosomes.id) group by\nchromosomes.number, ltr_repeats.presence_in_sativa order by\nchromosomes.number, ltr_repeats.presence_in_sativa;	solo ltrs presence in sativa stats by chromosome
3	select chromosomes.number, ltr_repeats.presence_in_sativa,\ncount(ltr_repeats.repeat_id) from ltr_repeats join repeats on\n(ltr_repeats.repeat_id = repeats.id and is_complete = true) join\nsequences on (repeats.sequence_id = sequences.id) join chromosomes on\n(sequences.chromosome_id = chromosomes.id) group by\nchromosomes.number, ltr_repeats.presence_in_sativa order by\nchromosomes.number, ltr_repeats.presence_in_sativa;	complete ltrs presence in sativa stats by chromosome
5	select repeats_order, count(*) from repeats join genes on (repeats.sequence_id = genes.sequence_id) left join scaffolds on (repeats.sequence_id = scaffolds.sequence_id) where genes.x <= repeats.x and repeats.y <= genes.y group by repeats_order	genes containing tes group by repeats order
6	select s.id, count(r.id) from repeats r join repeats s on (r.sequence_id = s.sequence_id) where r.id != s.id and r.x >= s.x and r.y <= s.y and r.sequence_id = 321 group by s.id order by s.id	Number of inserted elements
7	select repeats_order, count(*) from repeats join genes on (repeats.sequence_id = genes.sequence_id) where genes.x <= repeats.x and repeats.y <= genes.y group by repeats_order	TEs inside Genes
8	select genes.id,repeats.id, repeats_order from repeats join genes on (repeats.sequence_id = genes.sequence_id) where repeats.x <= genes.x and repeats.y > genes.x OR repeats.y >= genes.y and repeats.x < genes.y OR repeats.x > genes.x and repeats.y < genes.y group by genes.id,repeats.id, repeats_order	TEs Overlap Genes
\.


--
-- Name: queries_id_seq; Type: SEQUENCE SET; Schema: aux; Owner: genomes
--

SELECT pg_catalog.setval('queries_id_seq', 8, true);


--
-- Data for Name: site_blasts; Type: TABLE DATA; Schema: aux; Owner: genomes
--

COPY site_blasts (id, result, send_results_to, started, ended) FROM stdin;
\.


--
-- Name: site_blasts_id_seq; Type: SEQUENCE SET; Schema: aux; Owner: genomes
--

SELECT pg_catalog.setval('site_blasts_id_seq', 2, true);


SET search_path = sequence, pg_catalog;

--
-- Data for Name: chromosomes; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY chromosomes (id, number, species_id) FROM stdin;
9	9	157
8	8	157
7	7	157
6	6	157
5	5	157
4	4	157
3	3	157
2	2	157
12	12	157
11	11	157
10	10	157
1	1	157
\.


--
-- Name: chromosomes_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('chromosomes_id_seq', 152, true);


--
-- Data for Name: individuals; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY individuals (id, description, variety_id) FROM stdin;
\.


--
-- Name: individuals_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('individuals_id_seq', 12, true);


--
-- Data for Name: pseudomolecules; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY pseudomolecules (sequence_id, is_scaffold_derived, is_unplaced) FROM stdin;
\.


--
-- Data for Name: scaffolds; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY scaffolds (sequence_id, scaff_order, pseudomolecule_id, is_unplaced) FROM stdin;
\.


--
-- Data for Name: sequences; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY sequences (id, type, sequence_text, length, chromosome_id, superseded_by, date_modified, date_created, name, version) FROM stdin;
\.


--
-- Name: sequences_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('sequences_id_seq', 5508, true);


--
-- Data for Name: species; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY species (genus, species, subspecies, common_name, genome_type, id) FROM stdin;
Carthamus	tinctorius		safflower	\N	1
Caulerpa	taxifolia		alga, multinucleate	\N	2
Acyrthosiphon	pisum	pisum	pea aphid	\N	3
Centaurea	maculosa		spotted knapweed	\N	4
Centaurea	solstitialis		yellow star thistle	\N	5
Chara	sp.		alga, with sexual cells	\N	6
Chenopodium	quinoa		quinoa	\N	7
Chlamydomonas	reinhardtii		chlamydomonas	\N	8
Cichorium	endivia		endive	\N	9
Antirrhinum	majus	majus	snapdragon	\N	10
Cichorium	intybus		chicory	\N	11
Citrus	aurantium		citrus	\N	12
Citrus	reticulata		citrus	\N	13
Asd	asd	asd	e43c10	\N	14
Citrus	sinensis		citrus	\N	15
Clostridium	bifermantans		clostridium	\N	16
Coffea	arabica		coffee	\N	17
Coffea	canephora		coffee	\N	18
Coleochaete	orbicularis		alga, multicellular	\N	19
Manihot	esculenta		cassava	\N	20
Bemisia	tabaci	b-biotype	white fly	\N	21
Coleochaete	orbicularus		alga, multicellular	\N	22
Cucumis	melo		melon	\N	23
Cucumis	sativus		cucumber	\N	24
Curcuma	longa		turmeric	\N	25
Cynara	scolymus		artichoke	\N	26
Daucus	carota		\N	\N	27
Brassica	rapa	trilocularis	\N	\N	28
Cajanus	cajan	cajan	\N	\N	29
Draba	nivalis		\N	\N	30
Camellia	sinensis	bohea	tea	\N	31
Drosophila	albomicans		fruit fly	\N	32
Capsicum	frutescens	frutescens	pepper	\N	33
Drosophila	americana		fruit fly	\N	34
Drosophila	ananassae		fruit fly	\N	35
Drosophila	arizonae		fruit fly	\N	36
Drosophila	busckii		fruit fly	\N	37
Drosophila	erecta		fruit fly	\N	38
Drosophila	grimshawi		fruit fly	\N	39
Drosophila	hydei		fruit fly	\N	40
Drosophila	littoralis		fruit fly	\N	41
Chenopodium	quinoa	quinoa	quinoa	\N	42
Drosophila	mercatorum		fruit fly	\N	43
Chlamydomonas	reinhardtii	137c	chlamydomonas	\N	44
Chlamydomonas	reinhardtii	cw92 mt+	chlamydomonas	\N	45
Cicer	arietinum	arietinum	\N	\N	46
Drosophila	mimica		fruit fly	\N	47
Drosophila	mojavensis		fruit fly	\N	48
Drosophila	novamexicana		fruit fly	\N	49
Drosophila	persimilis		fruit fly	\N	50
Drosophila	repleta		fruit fly	\N	51
Drosophila	sechellia		fruit fly	\N	52
Drosophila	simulans		fruit fly	\N	53
Drosophila	virilis		fruit fly	\N	54
Coffea	eugeniodes	oficina	\N	\N	55
Drosophila	willistoni		fruit fly	\N	56
Drosophila	yakuba		fruit fly	\N	57
Eucalyptus	grandis		eucalyptus	\N	58
Ficus	citrifolia		fig	\N	59
Ficus	popenoei		fig	\N	60
Gallus	gallus		chicken	\N	61
Gerbera	hybrida		gerbera	\N	62
Ginglymostoma	cirratum		shark	\N	63
Gluconacetobacter	diazotrophicus		glucana	\N	64
Glycine	canescens		\N	\N	65
Glycine	cyrtoloba		\N	\N	66
Glycine	dolichocarpa		\N	\N	67
Glycine	falcata		\N	\N	68
Glycine	max		soybean	\N	69
Glycine	stenophita		\N	\N	70
Glycine	syndetika		soybean	\N	71
Glycine	tomentella		\N	\N	72
Gossypioides	kirkii		cotton	\N	73
Gossypium	arboreum		cotton	\N	74
Gossypium	exiguum		cotton	\N	75
Gossypium	herbaceum		cotton	\N	76
Gossypium	hirsutum		cotton	\N	77
Gossypium	raimondii		cotton	\N	78
Guizotia	abyssinica		\N	\N	79
Hamiltonella	defensa		bacterium	\N	80
Helianthus	annuus		sunflower	\N	81
Helianthus	argophyllus		silverleaf sunflower	\N	82
Helianthus	ciliaris		texas blueweed	\N	83
Helianthus	exilis		serpentine sunflower	\N	84
Euphorbia	esula	esula	\N	\N	85
Helianthus	paradoxus		paradox sunflower	\N	86
Ornithorhynchus	anatinus		platypus	\N	87
Oryza	alta		rice	\N	88
Oryza	australiensis		rice	\N	89
Oryza	barthii		rice	\N	90
Oryza	brachyantha		rice	\N	91
Oryza	coarctata		rice	\N	92
Oryza	eichingeri		\N	\N	93
Oryza	glumaepatula		rice	\N	94
Oryza	grandiglumis		\N	\N	95
Oryza	granulata		rice	\N	96
Glycine	max	max	\N	\N	97
Glycine	soja	soja	soybean	\N	98
Oryza	latifolia		rice	\N	99
Oryza	longistaminata		rice	\N	100
Oryza	meridionalis		rice	\N	101
Oryza	meyeriana		rice	\N	102
Oryza	minuta		rice	\N	103
Oryza	nivara		rice	\N	104
Oryza	officinalis		rice	\N	105
Oryza	punctata		rice	\N	106
Oryza	ridleyi		rice	\N	107
Oryza	rufipogon		rice	\N	108
Oryza	schlechteri		rice	\N	109
Parasponia	andersonii		parasponia	\N	110
Pegoscapus	estherae		fig wasp	\N	111
Pennisetum	ciliare		buffelgrass	\N	112
Petunia	hybrida		petunia	\N	113
Phalaenopsis	equestris		orchid	\N	114
Phaseolus	lunatus		bean	\N	115
Phaseolus	vulgaris		bean	\N	116
Poncirus	trifoliata		citrus	\N	117
Populus	deltoides		poplar	\N	118
Homo	sapiens	sapiens	human	\N	119
Populus	trichocarpa		poplar	\N	120
Hordeum	vulgare	spontaneum	barley	\N	121
Hordeum	vulgare	vulgare	barley	\N	122
Prunus	persica		peach	\N	123
Raphanus	sativus		radish	\N	124
Rattus	norveigicus		rat	\N	125
Rhodoferax	antarcticus		bacteria	\N	126
Rhodopila	globiformis		bacteria	\N	127
Saccharum	sp.		sugarcane	\N	128
Salmonella	typhimurium		salmonella	\N	129
Selaginella	moellendorfii		shining club moss	\N	130
Solanum	bulbocastanum		potato	\N	131
Solanum	demissum		potato	\N	132
Solanum	melongena		eggplant	\N	133
Solanum	tuberosum		potato	\N	134
Sorghum	bicolor		sorghum	\N	135
Sorghum	propinquum		sorghum	\N	136
Spinacia	oleracea		spinach	\N	137
Tachyglossus	aculeatus		echidna	\N	138
Taeniopygia	guttata		zebra finch	\N	139
Taraxacum	officinale		dandelion	\N	140
Thellungiella	halophila		salt cress	\N	141
Theobroma	cacao		\N	\N	142
Thermobia	domestica		firebrat	\N	143
Trifolium	pratense		clover	\N	144
Trifolium	repens		clover	\N	145
Triticum	aestivum		wheat	\N	146
Triticum	monococcum		wheat	\N	147
Ulmus	campestris		elm	\N	148
Nectria	haematococca	mpvi	fusarium	\N	149
Vigna	unguiculata		cowpea	\N	150
Vitis	vinifera		grape	\N	151
Volvox	carteri		\N	\N	152
Xenylla	pseudomaritima		springtail	\N	153
Zea	mays		maize	\N	154
Zingiber	officinale		ginger	\N	155
Zizania	palustris		rice	\N	156
Oryza	glaberrima		rice	AA	157
Oryza	sativa	indica	rice	\N	158
Oryza	sativa	japonica	rice	\N	159
Oryza	sativa	sativa	red rice	\N	160
Oryza	sativa l	indica	rice	\N	161
Oryza	sative	japonica	rice	\N	162
Persea	americana	drymifolia	avocado	\N	163
Phaseolus	vulgaris	mesoamerican	bean	\N	164
Phaseolus	vulgaris	mexicanus	bean	\N	165
Pyrenophora	teres	teres	fungus	\N	166
Saccharum	hybrid	cultivar(offic	sugarcane	\N	167
Triticum	aestivum	aestivum	wheat	\N	168
Vigna	unguiculata	unguiculata	cowpea	\N	169
Volvox	carteri	nagariensis	alga, colonial	\N	170
Yarrowia	lipolytica	e150	yarrowia	\N	171
Zea	mays	mays	maize	\N	172
Acorus	americanus		sweet flag	\N	173
Acorus	gramineus		\N	\N	174
Amaranthus	hypochondriacus		amaranth	\N	175
Amborella	trichocarpa		amborella	\N	176
Amborella	trichopoda		amborella	\N	177
Amborella	tricopoda		\N	\N	178
Angiopteris	evecta		angiopteris	\N	179
Anthoceros	formosae		hornwort	\N	180
Apis	mellifera		bee	\N	181
Apus	mellifera		bee	\N	182
Arabidopsis	thaliana		arabidopsis	\N	183
Ashbya	gossypii		ashbya	\N	184
Asparagus	officinalis		asparagus	\N	185
Asparagus	plumosus		asparagus	\N	186
Aspergillus	flavus		aspergillus	\N	187
Aspergillus	nidulans		aspergillus	\N	188
Barnadesia	spinosa		\N	\N	189
Biomphalaria	glabrata		snail	\N	190
Blumeria	graminis		fungus	\N	191
Boechera	holboellii		\N	\N	192
Boechera	stricta		\N	\N	193
Brachypodium	distachyon		brachypodium	\N	194
Bradyrhizobium	japonicum		bradyrhizobium	\N	195
Callithrix	jacchus		common marmoset	\N	196
Capsicum	annum		pepper	\N	197
Cardamine	hirsuta		\N	\N	198
Carsonella	ruddii		bacterium	\N	199
Helianthus	petiolaris		prairie sunflower	\N	200
Helianthus	tuberosus		jerusalem artichoke	\N	201
Hieracium	caespitosum		hawkweed	\N	202
Homo	sapiens		human	\N	203
Hordeum	vulgare		barley	\N	204
Lactuca	perennis		lettuce	\N	205
Lactuca	saligna		lettuce	\N	206
Lactuca	sativa		lettuce	\N	207
Lactuca	serriola		lettuce	\N	208
Lactuca	virosa		lettuce	\N	209
Lama	camelid		lama	\N	210
Leersia	perrieri		rice	\N	211
Liriodendron	tulipifera		liriodendron	\N	212
Lolium	perenne		ryegrass, perennial	\N	213
Lycopersicon	cheezmanii		tomato	\N	214
Lycopersicon	esculentum		tomato	\N	215
Lycopersicon	hirsutum		tomato	\N	216
Lycopersicum	esculentum		tomato	\N	217
Lycopersicum	pennellii		tomato	\N	218
Macaca	mulatta		rhesus monkey	\N	219
Macropus	eugenii		wallaby	\N	220
Magnaporthe	grisea		rice blast fungus	\N	221
Marchantia	polymorpha		liverwort	\N	222
Marselia	minuta		\N	\N	223
Medicago	truncatula		barrel medic	\N	224
Mesostigma	viride		alga, unicellular	\N	225
Mus	musculus		mouse	\N	226
Mycobacterium	ulcerans		bacterium	\N	227
Nicotiana	benthamiana		nicotiana	\N	228
Nuphar	advena		waterlily	\N	229
Ochrobactrum	anthropi		ochrobactrum	\N	230
Ocimum	basilicum		basil	\N	231
\.


--
-- Name: species_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('species_id_seq', 357, true);


--
-- Data for Name: varieties; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY varieties (name, alias, species_id, id) FROM stdin;
IRGC96717	CG14	\N	1
IRGC100122	\N	\N	2
IRGC100931	\N	\N	3
IRGC100934	\N	\N	4
IRGC103895	\N	\N	5
IRGC104084	\N	\N	6
IRGC104119	\N	\N	7
IRGC105608	\N	\N	8
IRGC106234	\N	\N	9
CG14	\N	\N	10
IRGC103469	\N	\N	11
TOG5457	\N	\N	12
TOG5467	\N	\N	13
TOG5923	\N	\N	14
TOG5949	\N	\N	15
TOG7025	\N	\N	16
TOG7102	\N	\N	17
\.


--
-- Name: varieties_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('varieties_id_seq', 29, true);


SET search_path = annotation, pg_catalog;

--
-- Name: dna_te_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY dna_te_repeats
    ADD CONSTRAINT dna_te_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: exons_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY exons
    ADD CONSTRAINT exons_pkey PRIMARY KEY (id);


--
-- Name: exons_x_y_mrna_id_key; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY exons
    ADD CONSTRAINT exons_x_y_mrna_id_key UNIQUE (x, y, mrna_id);


--
-- Name: genes_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY genes
    ADD CONSTRAINT genes_pkey PRIMARY KEY (id);


--
-- Name: helitron_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY helitron_repeats
    ADD CONSTRAINT helitron_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: line_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY line_repeats
    ADD CONSTRAINT line_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: ltr_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY ltr_repeats
    ADD CONSTRAINT ltr_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: mite_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY mite_repeats
    ADD CONSTRAINT mite_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: mrnas_name_key; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY mrnas
    ADD CONSTRAINT mrnas_name_key UNIQUE (name);


--
-- Name: mrnas_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY mrnas
    ADD CONSTRAINT mrnas_pkey PRIMARY KEY (id);


--
-- Name: mrnas_x_y_gene_id_key; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY mrnas
    ADD CONSTRAINT mrnas_x_y_gene_id_key UNIQUE (x, y, gene_id);


--
-- Name: repeats_classification_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY repeats_classification
    ADD CONSTRAINT repeats_classification_pkey PRIMARY KEY (id);


--
-- Name: repeats_classification_rclass_subclass_rorder_superfamily_f_key; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY repeats_classification
    ADD CONSTRAINT repeats_classification_rclass_subclass_rorder_superfamily_f_key UNIQUE (rclass, subclass, rorder, superfamily, family);


--
-- Name: repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT repeats_pkey PRIMARY KEY (id);


--
-- Name: rnas_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY rnas
    ADD CONSTRAINT rnas_pkey PRIMARY KEY (id);


--
-- Name: rnas_unique_name; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY rnas
    ADD CONSTRAINT rnas_unique_name UNIQUE (name);


--
-- Name: rnas_unique_position; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY rnas
    ADD CONSTRAINT rnas_unique_position UNIQUE (sequence_id, x, y);


--
-- Name: sine_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY sine_repeats
    ADD CONSTRAINT sine_repeats_pkey PRIMARY KEY (repeat_id);


--
-- Name: snps_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY snps
    ADD CONSTRAINT snps_pkey PRIMARY KEY (id);


--
-- Name: snps_sequence_id_pos_variety_id_key; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY snps
    ADD CONSTRAINT snps_sequence_id_pos_variety_id_key UNIQUE (sequence_id, pos, individual_id);


--
-- Name: unique_exon_name; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY exons
    ADD CONSTRAINT unique_exon_name UNIQUE (name);


--
-- Name: unique_gene_name; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY genes
    ADD CONSTRAINT unique_gene_name UNIQUE (name);


--
-- Name: unique_position; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY genes
    ADD CONSTRAINT unique_position UNIQUE (sequence_id, x, y);


--
-- Name: unique_position_repeat; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT unique_position_repeat UNIQUE (sequence_id, x, y);


--
-- Name: unknown_repeats_pkey; Type: CONSTRAINT; Schema: annotation; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY unknown_repeats
    ADD CONSTRAINT unknown_repeats_pkey PRIMARY KEY (repeat_id);


SET search_path = aux, pg_catalog;

--
-- Name: _sys_config_pkey; Type: CONSTRAINT; Schema: aux; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY _sys_config
    ADD CONSTRAINT _sys_config_pkey PRIMARY KEY (category, keyword);


--
-- Name: site_blasts_pkey; Type: CONSTRAINT; Schema: aux; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY site_blasts
    ADD CONSTRAINT site_blasts_pkey PRIMARY KEY (id);


SET search_path = sequence, pg_catalog;

--
-- Name: chromosomes_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY chromosomes
    ADD CONSTRAINT chromosomes_pkey PRIMARY KEY (id);


--
-- Name: individuals_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY individuals
    ADD CONSTRAINT individuals_pkey PRIMARY KEY (id);


--
-- Name: pseudomolecules_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY pseudomolecules
    ADD CONSTRAINT pseudomolecules_pkey PRIMARY KEY (sequence_id);


--
-- Name: scaffolds_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY scaffolds
    ADD CONSTRAINT scaffolds_pkey PRIMARY KEY (sequence_id);


--
-- Name: sequences_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY sequences
    ADD CONSTRAINT sequences_pkey PRIMARY KEY (id);


--
-- Name: species_id_key; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY species
    ADD CONSTRAINT species_id_key UNIQUE (id);


--
-- Name: species_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY species
    ADD CONSTRAINT species_pkey PRIMARY KEY (id);


--
-- Name: species_species_genus_subspecies_key; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY species
    ADD CONSTRAINT species_species_genus_subspecies_key UNIQUE (species, genus, subspecies);


--
-- Name: varieties_name_key; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY varieties
    ADD CONSTRAINT varieties_name_key UNIQUE (name);


--
-- Name: varieties_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY varieties
    ADD CONSTRAINT varieties_pkey PRIMARY KEY (id);


--
-- Name: fki_scaffolds_pseudomolecule_id_fkey; Type: INDEX; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE INDEX fki_scaffolds_pseudomolecule_id_fkey ON scaffolds USING btree (pseudomolecule_id);


SET search_path = annotation, pg_catalog;

--
-- Name: exons_mrna_id_fkey; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY exons
    ADD CONSTRAINT exons_mrna_id_fkey FOREIGN KEY (mrna_id) REFERENCES mrnas(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_dna_te_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY dna_te_repeats
    ADD CONSTRAINT fk_dna_te_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_gene_seq; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY genes
    ADD CONSTRAINT fk_gene_seq FOREIGN KEY (sequence_id) REFERENCES sequence.sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_helitron_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY helitron_repeats
    ADD CONSTRAINT fk_helitron_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_line_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY line_repeats
    ADD CONSTRAINT fk_line_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_ltr_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY ltr_repeats
    ADD CONSTRAINT fk_ltr_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_mite_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY mite_repeats
    ADD CONSTRAINT fk_mite_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_parent_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT fk_parent_repeat FOREIGN KEY (parent_repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: fk_repeats_repeats_classification; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT fk_repeats_repeats_classification FOREIGN KEY (repeats_classification_id) REFERENCES repeats_classification(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: fk_repeats_sequence; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT fk_repeats_sequence FOREIGN KEY (sequence_id) REFERENCES sequence.sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_similar_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT fk_similar_repeat FOREIGN KEY (similar_repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: fk_sine_repeats_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY sine_repeats
    ADD CONSTRAINT fk_sine_repeats_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_unknown_repeat; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY unknown_repeats
    ADD CONSTRAINT fk_unknown_repeat FOREIGN KEY (repeat_id) REFERENCES repeats(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: mrnas_gene_id_fkey; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY mrnas
    ADD CONSTRAINT mrnas_gene_id_fkey FOREIGN KEY (gene_id) REFERENCES genes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: rnas_parent_rna_id_fkey; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY rnas
    ADD CONSTRAINT rnas_parent_rna_id_fkey FOREIGN KEY (parent_rna_id) REFERENCES rnas(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: rnas_sequence; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY rnas
    ADD CONSTRAINT rnas_sequence FOREIGN KEY (sequence_id) REFERENCES sequence.sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: snps_individual_id_fkey; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY snps
    ADD CONSTRAINT snps_individual_id_fkey FOREIGN KEY (individual_id) REFERENCES sequence.individuals(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: snps_sequence_id_fkey; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY snps
    ADD CONSTRAINT snps_sequence_id_fkey FOREIGN KEY (sequence_id) REFERENCES sequence.sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


SET search_path = sequence, pg_catalog;

--
-- Name: fk_chromosomes_species; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY chromosomes
    ADD CONSTRAINT fk_chromosomes_species FOREIGN KEY (species_id) REFERENCES species(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_individuals_varieties; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY individuals
    ADD CONSTRAINT fk_individuals_varieties FOREIGN KEY (variety_id) REFERENCES varieties(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_pseudom_seq; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY pseudomolecules
    ADD CONSTRAINT fk_pseudom_seq FOREIGN KEY (sequence_id) REFERENCES sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_scaff_sequence; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY scaffolds
    ADD CONSTRAINT fk_scaff_sequence FOREIGN KEY (sequence_id) REFERENCES sequences(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_seq_chromosome_num; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY sequences
    ADD CONSTRAINT fk_seq_chromosome_num FOREIGN KEY (chromosome_id) REFERENCES chromosomes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_superceded_by; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY sequences
    ADD CONSTRAINT fk_superceded_by FOREIGN KEY (superseded_by) REFERENCES sequences(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: fk_varietiss_species; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY varieties
    ADD CONSTRAINT fk_varietiss_species FOREIGN KEY (species_id) REFERENCES species(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: scaffolds_pseudomolecule_id_fkey; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY scaffolds
    ADD CONSTRAINT scaffolds_pseudomolecule_id_fkey FOREIGN KEY (pseudomolecule_id) REFERENCES pseudomolecules(sequence_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

