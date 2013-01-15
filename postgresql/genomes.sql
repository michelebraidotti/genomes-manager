--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
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


ALTER TABLE annotation.dna_te_repeats OWNER TO genomes;

--
-- Name: exons_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE exons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE annotation.exons_id_seq OWNER TO genomes;

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


ALTER TABLE annotation.exons OWNER TO genomes;

--
-- Name: genes_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE genes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE annotation.genes_id_seq OWNER TO genomes;

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


ALTER TABLE annotation.genes OWNER TO genomes;

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


ALTER TABLE annotation.helitron_repeats OWNER TO genomes;

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


ALTER TABLE annotation.line_repeats OWNER TO genomes;

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


ALTER TABLE annotation.ltr_repeats OWNER TO genomes;

--
-- Name: mite_repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE mite_repeats (
    repeat_id integer NOT NULL,
    tsd_seq character varying(3) DEFAULT NULL::character varying,
    tir_x integer,
    tir_y integer
);


ALTER TABLE annotation.mite_repeats OWNER TO genomes;

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


ALTER TABLE annotation.mrnas OWNER TO genomes;

--
-- Name: mrnas_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE mrnas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE annotation.mrnas_id_seq OWNER TO genomes;

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


ALTER TABLE annotation.repeats_id_seq OWNER TO genomes;

--
-- Name: repeats; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE repeats (
    id integer DEFAULT nextval('repeats_id_seq'::regclass) NOT NULL,
    type character varying(10) DEFAULT '--'::character varying NOT NULL,
    sequence_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    strandness character(1) DEFAULT '+'::bpchar NOT NULL,
    repeats_class character varying(25) NOT NULL,
    repeats_subclass character varying(25) NOT NULL,
    repeats_order character varying(45) NOT NULL,
    repeats_superfamily character varying(45) NOT NULL,
    repeats_family character varying(45) NOT NULL,
    parent_repeat_id integer,
    similar_repeat_id integer,
    gc_content numeric(5,2) DEFAULT NULL::numeric,
    contained_elements_count integer DEFAULT 0,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL,
    notes text
);


ALTER TABLE annotation.repeats OWNER TO genomes;

--
-- Name: repeats_classification; Type: TABLE; Schema: annotation; Owner: genomes; Tablespace: 
--

CREATE TABLE repeats_classification (
    rclass character varying(25) NOT NULL,
    subclass character varying(25) NOT NULL,
    rorder character varying(45) NOT NULL,
    superfamily character varying(45) NOT NULL,
    family character varying(45) NOT NULL
);


ALTER TABLE annotation.repeats_classification OWNER TO genomes;

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


ALTER TABLE annotation.rnas OWNER TO genomes;

--
-- Name: rnas_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE rnas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE annotation.rnas_id_seq OWNER TO genomes;

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


ALTER TABLE annotation.sine_repeats OWNER TO genomes;

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


ALTER TABLE annotation.snps OWNER TO genomes;

--
-- Name: snps_id_seq; Type: SEQUENCE; Schema: annotation; Owner: genomes
--

CREATE SEQUENCE snps_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE annotation.snps_id_seq OWNER TO genomes;

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


ALTER TABLE annotation.unknown_repeats OWNER TO genomes;

SET search_path = aux, pg_catalog;

--
-- Name: _sys_config; Type: TABLE; Schema: aux; Owner: genomes; Tablespace: 
--

CREATE TABLE _sys_config (
    category character varying(15) NOT NULL,
    keyword character varying(155) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE aux._sys_config OWNER TO genomes;

--
-- Name: queries; Type: TABLE; Schema: aux; Owner: genomes; Tablespace: 
--

CREATE TABLE queries (
    id integer NOT NULL,
    query text,
    description text
);


ALTER TABLE aux.queries OWNER TO genomes;

--
-- Name: queries_id_seq; Type: SEQUENCE; Schema: aux; Owner: genomes
--

CREATE SEQUENCE queries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aux.queries_id_seq OWNER TO genomes;

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


ALTER TABLE aux.site_blasts_id_seq OWNER TO genomes;

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


ALTER TABLE aux.site_blasts OWNER TO genomes;

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


ALTER TABLE sequence.chromosomes_id_seq OWNER TO genomes;

--
-- Name: chromosomes; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE chromosomes (
    id integer DEFAULT nextval('chromosomes_id_seq'::regclass) NOT NULL,
    number character varying(4) NOT NULL,
    species_genus character varying(45) NOT NULL,
    species_species character varying(45) NOT NULL,
    species_subspecies character varying(45) NOT NULL
);


ALTER TABLE sequence.chromosomes OWNER TO genomes;

--
-- Name: individuals; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE individuals (
    id integer NOT NULL,
    variety_name character varying(55) NOT NULL,
    description character varying(255)
);


ALTER TABLE sequence.individuals OWNER TO genomes;

--
-- Name: individuals_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE individuals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence.individuals_id_seq OWNER TO genomes;

--
-- Name: individuals_id_seq; Type: SEQUENCE OWNED BY; Schema: sequence; Owner: genomes
--

ALTER SEQUENCE individuals_id_seq OWNED BY individuals.id;


--
-- Name: pseudomolecules; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE pseudomolecules (
    sequence_id integer NOT NULL,
    name character varying(45) NOT NULL,
    version character varying(9) NOT NULL,
    is_scaffold_derived boolean DEFAULT false NOT NULL,
    is_unplaced boolean DEFAULT false NOT NULL
);


ALTER TABLE sequence.pseudomolecules OWNER TO genomes;

--
-- Name: scaffolds; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE scaffolds (
    sequence_id integer NOT NULL,
    name character varying(55) NOT NULL,
    scaff_version character varying(5) NOT NULL,
    scaff_order integer DEFAULT 0 NOT NULL,
    pseudomolecule_id integer,
    is_unplaced boolean DEFAULT false NOT NULL
);


ALTER TABLE sequence.scaffolds OWNER TO genomes;

--
-- Name: sequences_id_seq; Type: SEQUENCE; Schema: sequence; Owner: genomes
--

CREATE SEQUENCE sequences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sequence.sequences_id_seq OWNER TO genomes;

--
-- Name: sequences; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE sequences (
    id integer DEFAULT nextval('sequences_id_seq'::regclass) NOT NULL,
    type character varying(15) DEFAULT '--'::character varying NOT NULL,
    sequence text,
    length integer DEFAULT 0 NOT NULL,
    chromosome_id integer NOT NULL,
    superseded_by integer,
    date_modified timestamp without time zone DEFAULT now() NOT NULL,
    date_created timestamp without time zone NOT NULL
);


ALTER TABLE sequence.sequences OWNER TO genomes;

--
-- Name: species; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE species (
    genus character varying(45) NOT NULL,
    species character varying(45) NOT NULL,
    subspecies character varying(45) NOT NULL,
    common_name character varying(45) DEFAULT NULL::character varying,
    genome_type character varying(15) DEFAULT NULL::character varying
);


ALTER TABLE sequence.species OWNER TO genomes;

--
-- Name: varieties; Type: TABLE; Schema: sequence; Owner: genomes; Tablespace: 
--

CREATE TABLE varieties (
    name character varying(55) NOT NULL,
    genus character varying(45) NOT NULL,
    species character varying(45) NOT NULL,
    subspecies character varying(45) NOT NULL,
    alias character varying(55)
);


ALTER TABLE sequence.varieties OWNER TO genomes;

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

SELECT pg_catalog.setval('exons_id_seq', 142090, true);


--
-- Data for Name: genes; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY genes (id, name, type, sequence_id, x, y, strandness, gc_content, date_modified, date_created) FROM stdin;
\.


--
-- Name: genes_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('genes_id_seq', 33172, true);


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

SELECT pg_catalog.setval('mrnas_id_seq', 33166, true);


--
-- Data for Name: repeats; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY repeats (id, type, sequence_id, x, y, strandness, repeats_class, repeats_subclass, repeats_order, repeats_superfamily, repeats_family, parent_repeat_id, similar_repeat_id, gc_content, contained_elements_count, date_modified, date_created, notes) FROM stdin;
\.


--
-- Data for Name: repeats_classification; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY repeats_classification (rclass, subclass, rorder, superfamily, family) FROM stdin;
I	I	LINE	NA	NA
I	I	LTR	Copia	ATCOPIA17I
I	I	LTR	Copia	ATCOPIA24I
I	I	LTR	Copia	ATCOPIA57
I	I	LTR	Copia	ATCOPIA59
I	I	LTR	Copia	ATCOPIA64
I	I	LTR	Copia	ATCOPIA77
I	I	LTR	Copia	COP1
I	I	LTR	Copia	COP18
I	I	LTR	Copia	COPI1
I	I	LTR	Copia	COPI3
I	I	LTR	Copia	COPIA-1
I	I	LTR	Copia	COPIA-10
I	I	LTR	Copia	COPIA-101
I	I	LTR	Copia	COPIA-103
I	I	LTR	Copia	COPIA-104
I	I	LTR	Copia	COPIA-11
I	I	LTR	Copia	COPIA-12
I	I	LTR	Copia	COPIA-120
I	I	LTR	Copia	COPIA-129
I	I	LTR	Copia	COPIA-13
I	I	LTR	Copia	COPIA-132
I	I	LTR	Copia	COPIA-133
I	I	LTR	Copia	COPIA-139
I	I	LTR	Copia	COPIA-15
I	I	LTR	Copia	COPIA-16
I	I	LTR	Copia	COPIA-18
I	I	LTR	Copia	COPIA-19
I	I	LTR	Copia	COPIA-23
I	I	LTR	Copia	COPIA-24
I	I	LTR	Copia	COPIA-28
I	I	LTR	Copia	COPIA-30
I	I	LTR	Copia	COPIA-31
I	I	LTR	Copia	COPIA-35
I	I	LTR	Copia	COPIA-36
I	I	LTR	Copia	COPIA-37
I	I	LTR	Copia	COPIA-38
I	I	LTR	Copia	COPIA-39
I	I	LTR	Copia	COPIA-40
I	I	LTR	Copia	COPIA-42
I	I	LTR	Copia	COPIA-43
I	I	LTR	Copia	COPIA-44
I	I	LTR	Copia	COPIA-45
I	I	LTR	Copia	COPIA-46
I	I	LTR	Copia	COPIA-47
I	I	LTR	Copia	COPIA-50
I	I	LTR	Copia	COPIA-55
I	I	LTR	Copia	COPIA-58
I	I	LTR	Copia	COPIA-59
I	I	LTR	Copia	COPIA-60
I	I	LTR	Copia	COPIA-64
I	I	LTR	Copia	COPIA-67
I	I	LTR	Copia	COPIA-68
I	I	LTR	Copia	COPIA-7
I	I	LTR	Copia	COPIA-70
I	I	LTR	Copia	COPIA-72
I	I	LTR	Copia	COPIA-73
I	I	LTR	Copia	COPIA-75
I	I	LTR	Copia	COPIA-77
I	I	LTR	Copia	COPIA-78
I	I	LTR	Copia	COPIA-8
I	I	LTR	Copia	COPIA-82
I	I	LTR	Copia	COPIA-84
I	I	LTR	Copia	COPIA-86
I	I	LTR	Copia	COPIA-93
I	I	LTR	Copia	COPIA-95
I	I	LTR	Copia	COPIA-96
I	I	LTR	Copia	COPIA/A
I	I	LTR	Copia	COPIA/ACGS
I	I	LTR	Copia	COPIA/B
I	I	LTR	Copia	COPIA/BCGS
I	I	LTR	Copia	COPIA/C
I	I	LTR	Copia	COPIA/CCGS
I	I	LTR	Copia	COPIA/D
I	I	LTR	Copia	COPIA/DCGS
I	I	LTR	Copia	COPIA/E
I	I	LTR	Copia	COPIA/ECGS
I	I	LTR	Copia	COPIA/F
I	I	LTR	Copia	COPIA/G
I	I	LTR	Copia	COPIA/GCGS
I	I	LTR	Copia	COPIA/H
I	I	LTR	Copia	COPIA/I
I	I	LTR	Copia	COPIA/ICGS
I	I	LTR	Copia	COPIA/JCGS
I	I	LTR	Copia	COPIA/KCGS
I	I	LTR	Copia	COPIA/L
I	I	LTR	Copia	COPIA/LCGS
I	I	LTR	Copia	COPIA/NCGS
I	I	LTR	Copia	COPIA/OCGS
I	I	LTR	Copia	COPIA/PCGS
I	I	LTR	Copia	COPIA/QCGS
I	I	LTR	Copia	COPIA/RCGS
I	I	LTR	Copia	COPIA/TCGS
I	I	LTR	Copia	COPIA1
I	I	LTR	Copia	COPIA10
I	I	LTR	Copia	COPIA11
I	I	LTR	Copia	COPIA12
I	I	LTR	Copia	COPIA15
I	I	LTR	Copia	COPIA16
I	I	LTR	Copia	COPIA17
I	I	LTR	Copia	COPIA18
I	I	LTR	Copia	COPIA19
I	I	LTR	Copia	COPIA2
I	I	LTR	Copia	COPIA2-I
I	I	LTR	Copia	COPIA22
I	I	LTR	Copia	COPIA24
I	I	LTR	Copia	COPIA29
I	I	LTR	Copia	COPIA3
I	I	LTR	Copia	COPIA34
I	I	LTR	Copia	COPIA4
I	I	LTR	Copia	COPIA42
I	I	LTR	Copia	COPIA5
I	I	LTR	Copia	COPIA6
I	I	LTR	Copia	COPIA7
I	I	LTR	Copia	COPIA8
I	I	LTR	Copia	COPIA9
I	I	LTR	Copia	COPIO
I	I	LTR	Copia	CPR1
I	I	LTR	Copia	CPSC
I	I	LTR	Copia	CPSC2
I	I	LTR	Copia	CPSC3
I	I	LTR	Copia	CPSC4A
I	I	LTR	Copia	CPSC4B
I	I	LTR	Copia	CPSC5
I	I	LTR	Copia	CRM
I	I	LTR	Copia	CRMA1
I	I	LTR	Copia	HOPSCOTCH
I	I	LTR	Copia	OSCOPIA2
I	I	LTR	Copia	OSTONOR1
I	I	LTR	Copia	OSTONOR2
I	I	LTR	Copia	OSTONOR3
I	I	LTR	Copia	OSTONOR4
I	I	LTR	Copia	OSTONOR5
I	I	LTR	Copia	RETROFIT
I	I	LTR	Copia	RETROFIT2
I	I	LTR	Copia	RETROFIT3
I	I	LTR	Copia	RETROFIT4
I	I	LTR	Copia	RETROFIT5
I	I	LTR	Copia	RETROFIT6
I	I	LTR	Copia	RETROFIT7
I	I	LTR	Copia	RETROSAT2
I	I	LTR	Copia	RIRE1
I	I	LTR	Copia	RIRE2
I	I	LTR	Copia	RIRE3
I	I	LTR	Copia	RIRE5
I	I	LTR	Copia	RN107
I	I	LTR	Copia	SC-1
I	I	LTR	Copia	SC-10
I	I	LTR	Copia	SC-3
I	I	LTR	Copia	SC-4
I	I	LTR	Copia	SC-5
I	I	LTR	Copia	SC-6
I	I	LTR	Copia	SC-7
I	I	LTR	Copia	SC-8
I	I	LTR	Copia	SC-9
I	I	LTR	Copia	SC9
I	I	LTR	Copia	SC9A
I	I	LTR	Copia	SZ-10
I	I	LTR	Copia	SZ-5
I	I	LTR	Copia	SZ-55
I	I	LTR	Copia	SZ-6
I	I	LTR	Copia	UNCLASSIFIED
I	I	LTR	Gypsy	ATHILA
I	I	LTR	Gypsy	ATLANTYS
I	I	LTR	Gypsy	BAJIE
I	I	LTR	Gypsy	BEL
I	I	LTR	Gypsy	CENTROMERIC
I	I	LTR	Gypsy	CRM
I	I	LTR	Gypsy	CRMA
I	I	LTR	Gypsy	CRMA1
I	I	LTR	Gypsy	GYPSI
I	I	LTR	Gypsy	GYPSIA
I	I	LTR	Gypsy	GYPSO
I	I	LTR	Gypsy	GYPSOR1
I	I	LTR	Gypsy	GYPSY
I	I	LTR	Gypsy	GYPSY-1
I	I	LTR	Gypsy	GYPSY-10
I	I	LTR	Gypsy	GYPSY-105
I	I	LTR	Gypsy	GYPSY-108
I	I	LTR	Gypsy	GYPSY-109
I	I	LTR	Gypsy	GYPSY-11
I	I	LTR	Gypsy	GYPSY-113
I	I	LTR	Gypsy	GYPSY-116
I	I	LTR	Gypsy	GYPSY-117
I	I	LTR	Gypsy	GYPSY-12
I	I	LTR	Gypsy	GYPSY-120
I	I	LTR	Gypsy	GYPSY-122
I	I	LTR	Gypsy	GYPSY-125
I	I	LTR	Gypsy	GYPSY-132
I	I	LTR	Gypsy	GYPSY-14
I	I	LTR	Gypsy	GYPSY-150
I	I	LTR	Gypsy	GYPSY-16
I	I	LTR	Gypsy	GYPSY-17
I	I	LTR	Gypsy	GYPSY-18
I	I	LTR	Gypsy	GYPSY-19
I	I	LTR	Gypsy	GYPSY-20
I	I	LTR	Gypsy	GYPSY-21
I	I	LTR	Gypsy	GYPSY-22
I	I	LTR	Gypsy	GYPSY-23
I	I	LTR	Gypsy	GYPSY-25
I	I	LTR	Gypsy	GYPSY-27
I	I	LTR	Gypsy	GYPSY-28
I	I	LTR	Gypsy	GYPSY-30
I	I	LTR	Gypsy	GYPSY-31
I	I	LTR	Gypsy	GYPSY-32
I	I	LTR	Gypsy	GYPSY-34
I	I	LTR	Gypsy	GYPSY-38
I	I	LTR	Gypsy	GYPSY-39
I	I	LTR	Gypsy	GYPSY-41
I	I	LTR	Gypsy	GYPSY-48
I	I	LTR	Gypsy	GYPSY-51
I	I	LTR	Gypsy	GYPSY-53
I	I	LTR	Gypsy	GYPSY-55
I	I	LTR	Gypsy	GYPSY-59
I	I	LTR	Gypsy	GYPSY-6
I	I	LTR	Gypsy	GYPSY-61
I	I	LTR	Gypsy	GYPSY-62
I	I	LTR	Gypsy	GYPSY-70
I	I	LTR	Gypsy	GYPSY-71
I	I	LTR	Gypsy	GYPSY-73
I	I	LTR	Gypsy	GYPSY-74
I	I	LTR	Gypsy	GYPSY-75
I	I	LTR	Gypsy	GYPSY-79
I	I	LTR	Gypsy	GYPSY-8
I	I	LTR	Gypsy	GYPSY-82
I	I	LTR	Gypsy	GYPSY-84
I	I	LTR	Gypsy	GYPSY-9
I	I	LTR	Gypsy	GYPSY-91
I	I	LTR	Gypsy	GYPSY-92
I	I	LTR	Gypsy	GYPSY-99
I	I	LTR	Gypsy	GYPSY-A
I	I	LTR	Gypsy	GYPSY-B
I	I	LTR	Gypsy	GYPSY/ACGS
I	I	LTR	Gypsy	GYPSY/ATLANTYS
I	I	LTR	Gypsy	GYPSY/BAJIE
I	I	LTR	Gypsy	GYPSY/BCGS
I	I	LTR	Gypsy	GYPSY/C
I	I	LTR	Gypsy	GYPSY/CCGS
I	I	LTR	Gypsy	GYPSY/ECGS
I	I	LTR	Gypsy	GYPSY/FCGS
I	I	LTR	Gypsy	GYPSY/GYPSY1
I	I	LTR	Gypsy	GYPSY/J
I	I	LTR	Gypsy	GYPSY/K
I	I	LTR	Gypsy	GYPSY/O
I	I	LTR	Gypsy	GYPSY/Q
I	I	LTR	Gypsy	GYPSY/RETROSAT2
I	I	LTR	Gypsy	GYPSY/RIRE2
I	I	LTR	Gypsy	GYPSY/SZ-50
I	I	LTR	Gypsy	GYPSY/T
I	I	LTR	Gypsy	GYPSY1
I	I	LTR	Gypsy	GYPSY1-SB
I	I	LTR	Gypsy	GYPSY1-SM
I	I	LTR	Gypsy	GYPSY10
I	I	LTR	Gypsy	GYPSY11
I	I	LTR	Gypsy	GYPSY118
I	I	LTR	Gypsy	GYPSY123
I	I	LTR	Gypsy	GYPSY14
I	I	LTR	Gypsy	GYPSY15
I	I	LTR	Gypsy	GYPSY18
I	I	LTR	Gypsy	GYPSY19
I	I	LTR	Gypsy	GYPSY2
I	I	LTR	Gypsy	GYPSY20
I	I	LTR	Gypsy	GYPSY23
I	I	LTR	Gypsy	GYPSY24
I	I	LTR	Gypsy	GYPSY26
I	I	LTR	Gypsy	GYPSY29
I	I	LTR	Gypsy	GYPSY3
I	I	LTR	Gypsy	GYPSY31
I	I	LTR	Gypsy	GYPSY33
I	I	LTR	Gypsy	GYPSY34
I	I	LTR	Gypsy	GYPSY35
I	I	LTR	Gypsy	GYPSY36
I	I	LTR	Gypsy	GYPSY37
I	I	LTR	Gypsy	GYPSY39
I	I	LTR	Gypsy	GYPSY4
I	I	LTR	Gypsy	GYPSY40
I	I	LTR	Gypsy	GYPSY42
I	I	LTR	Gypsy	GYPSY46
I	I	LTR	Gypsy	GYPSY47
I	I	LTR	Gypsy	GYPSY5
I	I	LTR	Gypsy	GYPSY52
I	I	LTR	Gypsy	GYPSY56
I	I	LTR	Gypsy	GYPSY59
I	I	LTR	Gypsy	GYPSY6
I	I	LTR	Gypsy	GYPSY61
I	I	LTR	Gypsy	GYPSY62
I	I	LTR	Gypsy	GYPSY65
I	I	LTR	Gypsy	GYPSY68
I	I	LTR	Gypsy	GYPSY69
I	I	LTR	Gypsy	GYPSY7
I	I	LTR	Gypsy	GYPSY8
I	I	LTR	Gypsy	GYPSY80
I	I	LTR	Gypsy	GYPSY82
I	I	LTR	Gypsy	GYPSY9
I	I	LTR	Gypsy	GYPSY98
I	I	LTR	Gypsy	GYPSYA
I	I	LTR	Gypsy	GYPSYCHANGED48
I	I	LTR	Gypsy	GYPSYO
I	I	LTR	Gypsy	GYPSY_1
I	I	LTR	Gypsy	GYPSY_2
I	I	LTR	Gypsy	GYPSY_29
I	I	LTR	Gypsy	GYPSY_3
I	I	LTR	Gypsy	GYPSY_5
I	I	LTR	Gypsy	GYPSY_6
I	I	LTR	Gypsy	GYPSY_7
I	I	LTR	Gypsy	GYPSY_8
I	I	LTR	Gypsy	GYPSY_9
I	I	LTR	Gypsy	LTR/GYPSY/A
I	I	LTR	Gypsy	OSR3
I	I	LTR	Gypsy	OSR38
I	I	LTR	Gypsy	OSR39
I	I	LTR	Gypsy	OSR42
I	I	LTR	Gypsy	RETRO2
I	I	LTR	Gypsy	RETROFIT3
I	I	LTR	Gypsy	RETROFIT5
I	I	LTR	Gypsy	RETROFIT6
I	I	LTR	Gypsy	RETROSAT2
I	I	LTR	Gypsy	RETROSAT2A
I	I	LTR	Gypsy	RETROSAT3
I	I	LTR	Gypsy	RETROSAT4
I	I	LTR	Gypsy	RETROSAT5
I	I	LTR	Gypsy	RETROSAT6
I	I	LTR	Gypsy	RETROSOR2
I	I	LTR	Gypsy	RIRE2
I	I	LTR	Gypsy	RIRE22
I	I	LTR	Gypsy	RIRE3
I	I	LTR	Gypsy	RIRE3A
I	I	LTR	Gypsy	RIRE7
I	I	LTR	Gypsy	RIRE8
I	I	LTR	Gypsy	RIRE8A
I	I	LTR	Gypsy	RIRE8B
I	I	LTR	Gypsy	RIRE8C
I	I	LTR	Gypsy	RN
I	I	LTR	Gypsy	RN10
I	I	LTR	Gypsy	RN107
I	I	LTR	Gypsy	RN12
I	I	LTR	Gypsy	ROMANI1
I	I	LTR	Gypsy	SZ-10
I	I	LTR	Gypsy	SZ-22
I	I	LTR	Gypsy	SZ-26
I	I	LTR	Gypsy	SZ-35
I	I	LTR	Gypsy	SZ-39
I	I	LTR	Gypsy	SZ-4
I	I	LTR	Gypsy	SZ-42
I	I	LTR	Gypsy	SZ-43
I	I	LTR	Gypsy	SZ-46
I	I	LTR	Gypsy	SZ-48
I	I	LTR	Gypsy	SZ-49
I	I	LTR	Gypsy	SZ-5
I	I	LTR	Gypsy	SZ-50
I	I	LTR	Gypsy	SZ-52
I	I	LTR	Gypsy	SZ-53
I	I	LTR	Gypsy	SZ-54A
I	I	LTR	Gypsy	SZ-54C
I	I	LTR	Gypsy	SZ-54D
I	I	LTR	Gypsy	SZ-56
I	I	LTR	Gypsy	SZ-56A
I	I	LTR	Gypsy	SZ-57
I	I	LTR	Gypsy	SZ-61
I	I	LTR	Gypsy	SZ-62
I	I	LTR	Gypsy	SZ-63
I	I	LTR	Gypsy	SZ-64
I	I	LTR	Gypsy	SZ-64B
I	I	LTR	Gypsy	SZ-66
I	I	LTR	Gypsy	SZ-67
I	I	LTR	Gypsy	SZ-7
I	I	LTR	Gypsy	SZ-7A
I	I	LTR	Gypsy	SZ-8
I	I	LTR	Gypsy	SZ-9
I	I	LTR	Gypsy	SZ-COM
I	I	LTR	Gypsy	TRUNCATOR
I	I	LTR	Gypsy	UNCLASSIFIED
I	I	LTR	Unclassified	BEL
I	I	LTR	Unclassified	NA
I	I	LTR	Unclassified	SC-1
I	I	LTR	Unclassified	SZ
I	I	LTR	Unclassified	SZ-10
I	I	LTR	Unclassified	SZ-11
I	I	LTR	Unclassified	SZ-14
I	I	LTR	Unclassified	SZ-16
I	I	LTR	Unclassified	SZ-19
I	I	LTR	Unclassified	SZ-20
I	I	LTR	Unclassified	SZ-21
I	I	LTR	Unclassified	SZ-22
I	I	LTR	Unclassified	SZ-23
I	I	LTR	Unclassified	SZ-24
I	I	LTR	Unclassified	SZ-25
I	I	LTR	Unclassified	SZ-26
I	I	LTR	Unclassified	SZ-27
I	I	LTR	Unclassified	SZ-28
I	I	LTR	Unclassified	SZ-29
I	I	LTR	Unclassified	SZ-30
I	I	LTR	Unclassified	SZ-31
I	I	LTR	Unclassified	SZ-32
I	I	LTR	Unclassified	SZ-33
I	I	LTR	Unclassified	SZ-34
I	I	LTR	Unclassified	SZ-35
I	I	LTR	Unclassified	SZ-36
I	I	LTR	Unclassified	SZ-37
I	I	LTR	Unclassified	SZ-38
I	I	LTR	Unclassified	SZ-39
I	I	LTR	Unclassified	SZ-40
I	I	LTR	Unclassified	SZ-41
I	I	LTR	Unclassified	SZ-42
I	I	LTR	Unclassified	SZ-44
I	I	LTR	Unclassified	SZ-45
I	I	LTR	Unclassified	SZ-47
I	I	LTR	Unclassified	SZ-48
I	I	LTR	Unclassified	SZ-49
I	I	LTR	Unclassified	SZ-5
I	I	LTR	Unclassified	SZ-50
I	I	LTR	Unclassified	SZ-51
I	I	LTR	Unclassified	SZ-52
I	I	LTR	Unclassified	SZ-54
I	I	LTR	Unclassified	SZ-54D
I	I	LTR	Unclassified	SZ-56A
I	I	LTR	Unclassified	SZ-57
I	I	LTR	Unclassified	SZ-58
I	I	LTR	Unclassified	SZ-59
I	I	LTR	Unclassified	SZ-6
I	I	LTR	Unclassified	SZ-60
I	I	LTR	Unclassified	SZ-61
I	I	LTR	Unclassified	SZ-62
I	I	LTR	Unclassified	SZ-63
I	I	LTR	Unclassified	SZ-64
I	I	LTR	Unclassified	SZ-64B
I	I	LTR	Unclassified	SZ-65
I	I	LTR	Unclassified	SZ-66
I	I	LTR	Unclassified	SZ-67
I	I	LTR	Unclassified	SZ-7
I	I	LTR	Unclassified	SZ-7A
I	I	LTR	Unclassified	SZ-COM
I	I	LTR	Unclassified	TRUNCATOR2
I	I	SINE	NA	NA
I	I	SINE	NA	OSN1
I	I	SINE	NA	OSN2
I	I	SINE	NA	OSN3
I	I	SINE	NA	P-SINE1
I	I	SINE	NA	P-SINE2
I	I	SINE	NA	P-SINE3
II	I	DNA_TE	CACTA	NA
II	I	DNA_TE	EnSPm	NA
II	I	DNA_TE	hAT	NA
II	I	DNA_TE	Mariner	NA
II	I	DNA_TE	Mutator	NA
II	I	DNA_TE	NA	NA
II	I	DNA_TE	Other	NA
II	I	DNA_TE	Other	RMU3
II	I	DNA_TE	PIF-Harbinger	NA
II	I	DNA_TE	Stowaway	NA
II	I	DNA_TE	Tourist	NA
II	I	DNA_TE	Unclassified	NA
II	II	Helitron	Helitron	NA
II	III	MITE	NA	NA
UNKNOWN	UNKNOWN	UNKNOWN	UNKNOWN	UNKNOWN
II	I	DNA_TE	hAT	JOUZHENA
II	I	DNA_TE	Mutator	CLOUD-7
II	I	DNA_TE	hAT	HAT_25_SM_A
II	I	DNA_TE	Mutator	ATMU13_2P_M
II	III	MITE	Tourist	OLO24B
II	I	DNA_TE	hAT	HAT_21_SM_1P_C
II	III	MITE	Tourist	DITTO
II	III	MITE	Tourist	OLO24C
II	I	DNA_TE	hAT	HAT_22_SM_C
II	I	DNA_TE	hAT	HAT1_AGP_E
II	III	MITE	Tourist	OLO24
II	I	DNA_TE	hAT	HAT_22_SM_E
II	I	DNA_TE	Mutator	ATMU12_U
II	III	MITE	Turist	TOURIST6A_OS
II	III	MITE	Tourist	CASTAWAY
II	I	DNA_TE	Mariner	TNR11
II	I	DNA_TE	hAT	ATHAT1P_D
II	I	DNA_TE	hAT	ATHAT3P_D
II	I	DNA_TE	Mutator	VANDAL20P1_E
II	III	MITE	Mutator	MDM2
II	III	MITE	Tourist	BUHUI
II	I	DNA_TE	hAT	HAT_1_D
II	I	DNA_TE	Mutator	VANDAL20P1_G
II	I	DNA_TE	Mutator	CLOUD
II	I	DNA_TE	hAT	ATHAT10P_C
II	I	DNA_TE	hAT	QINNIU
II	III	MITE	Mutator	MDR1
II	I	DNA_TE	EnSPm	OSHOOTER
II	I	DNA_TE	hAT	HAT_20_SM_1P_B
II	I	DNA_TE	hAT	HAT_29_SM_A
II	III	MITE	Tourist	INDITTO
II	I	DNA_TE	Mutator	ATMU11P_A
II	I	DNA_TE	hAT	DS-RICE4N
II	I	DNA_TE	hAT	HAT_20_SM_1P_A
II	I	DNA_TE	hAT	TWIFB1
II	III	MITE	Tourist	OLO24A
II	I	DNA_TE	Mutator	ATMU11P_I
II	I	DNA_TE	hAT	ATHAT2P_C
II	III	MITE	Tourist	YOUREN
II	I	DNA_TE	hAT	JINHUA
II	I	DNA_TE	EnSPm	TNR3
II	I	DNA_TE	PIF-Harbinger	NDNA2TNA_OS
II	I	DNA_TE	PIF-Harbinger	NDNA1TNA_OS
II	I	DNA_TE	hAT	ATHAT1P_A
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_A
II	I	DNA_TE	Mutator	ATMU4P1_X
II	I	DNA_TE	hAT	HAT_22_SM_B
II	III	MITE	Tourist	SUSU
II	I	DNA_TE	Mutator	MUDRN5_OS
II	I	DNA_TE	hAT	F569
II	I	DNA_TE	hAT	HAT_21_SM_1P_B
II	III	MITE	Tourist	DITAILA
II	III	MITE	Tourist	STOLA
II	III	MITE	Tourist	STOLAB
II	I	DNA_TE	EnSPm	ATENSPM12_1P_H
II	I	DNA_TE	Mariner	OSMAR1
II	I	DNA_TE	EnSPm	ENSPM-10_OS
II	I	DNA_TE	EnSPm	ENSPM5_OS
II	I	DNA_TE	Mutator	CLOUD-4
II	I	DNA_TE	hAT	DS-RICE3N
II	I	DNA_TE	Mutator	AT10MU1_J
II	III	MITE	Tourist	QIQI
II	I	DNA_TE	PIF-Harbinger	KIDDOC
II	I	DNA_TE	Mutator	MUDRN4_OS
II	I	DNA_TE	EnSPm	ENSPM-13_OS
II	I	DNA_TE	EnSPm	MIDWAY
II	I	DNA_TE	EnSPm	ATENSPM12_1P_D
II	I	DNA_TE	EnSPm	ENSPM_OS
II	I	DNA_TE	EnSPm	ATENSPM12_1P_G
II	I	DNA_TE	EnSPm	ENSPM3_OS
II	I	DNA_TE	hAT	ATHAT10P_B
II	I	DNA_TE	hAT	F118
II	I	DNA_TE	EnSPm	ATENSPM7P_D
II	I	DNA_TE	Mutator	AT10MU1_M
II	I	DNA_TE	Mutator	MUDRN1_OS
II	III	MITE	Tourist	ECSR
II	I	DNA_TE	hAT	HAT_21_SM_1P_A
II	I	DNA_TE	hAT	TESS
II	I	DNA_TE	Unclassified	MICROPON-LIKE-1
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_C
II	III	MITE	Tourist	STONE
II	I	DNA_TE	Mutator	ATMU11P_J
II	III	MITE	Tourist	COWARD
II	I	DNA_TE	hAT	HAT_25_SM_B
II	I	DNA_TE	EnSPm	ENSPM-11_OS
II	I	DNA_TE	Mutator	AT10MU1_L
II	I	DNA_TE	hAT	ATHAT2P_B
II	I	DNA_TE	Mutator	CLOUD-3
II	I	DNA_TE	EnSPm	ENSPM-N2_OS
II	I	DNA_TE	hAT	CRATA-2
II	I	DNA_TE	Mutator	MUDRN2_OS
II	I	DNA_TE	EnSPm	ENSPM-12_OS
II	I	DNA_TE	EnSPm	ATENSPM10P_D
II	I	DNA_TE	Mutator	CLOUD-2
II	I	DNA_TE	hAT	HAT_24_SM_A
II	III	MITE	Tourist	CASLET
II	I	DNA_TE	EnSPm	CHESTER_A
II	I	DNA_TE	hAT	ATHAT3P_B
II	I	DNA_TE	hAT	DS-RICE2N
II	III	MITE	Tourist	DITTO3
II	I	DNA_TE	EnSPm	ATENSPM10P_E
II	I	DNA_TE	Mutator	VANDAL14_1P_I
II	I	DNA_TE	Mutator	AT10MU1_E
II	I	DNA_TE	Mutator	MUDR3_OS
II	I	DNA_TE	Mutator	AT10MU1_C
II	I	DNA_TE	Mutator	CLOUD-6
II	III	MITE	Tourist	COWARD-3
II	III	MITE	Turist	ECR
II	III	MITE	Tourist	DITAIL
II	I	DNA_TE	Mariner	MARINER_10_A
II	I	DNA_TE	PIF-Harbinger	KIDDOD
II	I	DNA_TE	hAT	TEMPINDAS
II	I	DNA_TE	EnSPm	ATENSPM10P_A
II	I	DNA_TE	Mutator	MULEX_OS
II	I	DNA_TE	Mutator	MUDR1_OS
II	I	DNA_TE	hAT	ATHAT9_C
II	I	DNA_TE	hAT	CRATA
II	I	DNA_TE	hAT	THRIA
II	III	MITE	Turist	WUJI
II	I	DNA_TE	Mutator	ATMU11P_L
II	I	DNA_TE	PIF-Harbinger	HARB-N1_OS
II	I	DNA_TE	hAT	YIREN
II	I	DNA_TE	CACTA	CACTA-B
II	I	DNA_TE	Mutator	ATMU11P_T
II	III	MITE	Tourist	CASGRANDA
II	III	MITE	Turist	GLUTEL1LIKE
II	I	DNA_TE	EnSPm	ENSPM-15_OS
II	I	DNA_TE	hAT	HAT_27_SM_B
II	I	DNA_TE	Unclassified	RCH2
II	I	DNA_TE	EnSPm	ATENSPM10P_B
II	I	DNA_TE	EnSPm	ENSPM4_OS
II	I	DNA_TE	CACTA	CACTA-H
II	I	DNA_TE	Mutator	ATMU11P_G
II	I	DNA_TE	EnSPm	ENSPM2_OS
II	I	DNA_TE	Mutator	MUDR4_OS
II	I	DNA_TE	EnSPm	ENSPM6_OS
II	I	DNA_TE	EnSPm	ATENSPM10P_C
II	I	DNA_TE	Mutator	ATMU4P1_V
II	I	DNA_TE	hAT	HAT1_AGP_D
II	I	DNA_TE	hAT	ATHAT10P_F
II	I	DNA_TE	Mutator	MU_OS
II	III	MITE	Tourist	NONAME
II	I	DNA_TE	hAT	DEBOAT
II	I	DNA_TE	Mutator	MUJITOS2
II	I	DNA_TE	Mutator	AT10MU1_N
II	I	DNA_TE	Mutator	AT10MU1_R
II	I	DNA_TE	Mutator	VANDAL20P1_F
II	I	DNA_TE	EnSPm	SPMLIKE
II	III	MITE	Tourist	HELIA
II	I	DNA_TE	hAT	DELAY
II	III	MITE	Tourist	TRC1
II	I	DNA_TE	CACTA	CACTA-D
II	I	DNA_TE	CACTA	CACTA-G1
II	I	DNA_TE	Mutator	ATMU11P_C
II	I	DNA_TE	EnSPm	ENSPM7_OS
II	I	DNA_TE	hAT	HAT2_A
II	I	DNA_TE	PIF-Harbinger	KIDDOA
II	III	MITE	Mutator	MDR3
II	I	DNA_TE	Mutator	ATMU11P_R
II	I	DNA_TE	Mutator	ATMU12_Y
II	I	DNA_TE	Mutator	ATMU12_X
II	I	DNA_TE	Mariner	MARINER_1_C
II	I	DNA_TE	EnSPm	ATENSPM10P_G
II	I	DNA_TE	Mutator	TNR8-1
II	I	DNA_TE	hAT	HAT_22_SM_A
II	III	MITE	Mutator	MDR2
II	III	MITE	Tourist	F770
II	I	DNA_TE	EnSPm	ENSPM-16_OS
II	I	DNA_TE	PIF-Harbinger	KIDDOB
II	I	DNA_TE	EnSPm	ENSPM-8_OS
II	I	DNA_TE	hAT	ATHAT7P_C
II	I	DNA_TE	hAT	TESSBIG
II	I	DNA_TE	Mutator	AT10MU1_S
II	I	DNA_TE	Mutator	ATMU11P_N
II	I	DNA_TE	Mutator	ATMU11P_Q
II	I	DNA_TE	hAT	ATHAT10P_A
II	III	MITE	Tourist	FOCUS
II	I	DNA_TE	hAT	HATOS1
II	III	MITE	Tourist	CASBIG
II	I	DNA_TE	Mutator	ATMU11P_P
II	I	DNA_TE	hAT	JOUZHEN
II	I	DNA_TE	Mutator	OSMU5
II	I	DNA_TE	hAT	HAT_27_SM_A
II	I	DNA_TE	Mutator	MUDRN3_OS
II	I	DNA_TE	Mutator	VANDAL21P1_R
II	I	DNA_TE	Mutator	AT10MU1_H
II	I	DNA_TE	Mutator	MUDROS2
II	I	DNA_TE	hAT	TAG2P_B
II	I	DNA_TE	EnSPm	ENSPM-9_OS
II	I	DNA_TE	PIF-Harbinger	ATIS112A_A
II	I	DNA_TE	CACTA	CACTA-I
II	I	DNA_TE	Mutator	AT10MU1_I
II	I	DNA_TE	Mutator	ATMU11P_D
II	I	DNA_TE	Mutator	AT10MU1_D
II	I	DNA_TE	Mutator	ATMU11P_W
II	III	MITE	Turist	HEARTBLEEDING
II	I	DNA_TE	Mutator	OSMU6
II	I	DNA_TE	Mutator	AT10MU1_F
II	I	DNA_TE	Mutator	AT10MU1_O
II	I	DNA_TE	hAT	ATHAT7P_F
II	I	DNA_TE	Mutator	VANDAL9_1P_H
II	I	DNA_TE	Mutator	AT10MU1_B
II	I	DNA_TE	Mutator	MUJITOS1
II	I	DNA_TE	Mariner	MARINER_1_A
II	I	DNA_TE	Mutator	ATMU11P_B
II	III	MITE	Tourist	CENTRE
II	I	DNA_TE	PIF-Harbinger	ATIS112A_B
II	I	DNA_TE	Mutator	AT10MU1_G
II	I	DNA_TE	Mutator	AT10MU1_P
II	I	DNA_TE	EnSPm	ENSPM-N1_OS
II	III	MITE	Mutator	MDM1
II	I	DNA_TE	Mutator	OSMUD1
II	I	DNA_TE	EnSPm	ATENSPM12_1P_E
II	I	DNA_TE	Mutator	MUDROS1
II	I	DNA_TE	Mariner	MARINER_1_D
II	I	DNA_TE	Mutator	MUDR-5_OS
II	I	DNA_TE	Mariner	MARINER_1_B
II	I	DNA_TE	hAT	ATHAT10P_D
II	I	DNA_TE	Mutator	MUDR2_OS
II	I	DNA_TE	EnSPm	ATENSPM7P_A
II	I	DNA_TE	Mutator	RMU1A23
II	III	MITE	Tourist	CASIN
II	I	DNA_TE	Mutator	ATMU4P1_Z
II	I	DNA_TE	EnSPm	ENSPM-14_OS
II	I	DNA_TE	PIF-Harbinger	HARBINGER_2_BF_B
II	I	DNA_TE	Mutator	ATMU13_2P_P
II	I	DNA_TE	Mutator	AT10MU1_Q
II	III	MITE	Tourist	LIER
II	III	MITE	Tourist	CASMALL
II	I	DNA_TE	Mutator	CLOUD-5
II	I	DNA_TE	hAT	TAG2P_E
II	III	MITE	Mariner	DTT_OGLA_SA
II	III	MITE	Mariner	DTT_OGLA_SW
II	III	MITE	Mariner	DTT_OGLA_SH
II	III	MITE	Mariner	DTT_OGLA_SQ
II	III	MITE	Mariner	DTT_OGLA_SG
II	III	MITE	Mariner	DTT_OGLA_SJ
II	III	MITE	Mariner	DTT_OGLA_SS
II	III	MITE	Mariner	DTT_OGLA_SM
II	III	MITE	Mariner	DTT_OGLA_SE
II	III	MITE	Mariner	DTT_OGLA_SB
II	III	MITE	Mariner	DTT_OGLA_SD
II	III	MITE	Mariner	DTT_OGLA_SC
II	III	MITE	Mariner	DTT_OGLA_ST
II	III	MITE	Mariner	DTT_OGLA_SN
II	III	MITE	Mariner	DTT_OGLA_SAC
II	III	MITE	Mariner	DTT_OGLA_SV
II	III	MITE	Mariner	DTT_OGLA_SK
II	III	MITE	Mariner	DTT_OGLA_SU
II	III	MITE	Mariner	DTT_OGLA_SAA
II	III	MITE	Mariner	DTT_OGLA_SAB
II	III	MITE	Mariner	DTT_OGLA_SR
II	III	MITE	Mariner	DTT_OGLA_SD-423
II	III	MITE	Harbinger	DTH_OGLA_TF
II	III	MITE	Harbinger	DTH_OGLA_TC
II	III	MITE	Harbinger	DTH_OGLA_TE
II	III	MITE	Harbinger	DTH_OGLA_TB
II	III	MITE	Harbinger	DTH_OGLA_TA
II	III	MITE	Harbinger	DTH_OGLA_TD
II	III	MITE	Harbinger	DTH_OGLA_TF-2926
\.


--
-- Name: repeats_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('repeats_id_seq', 385679, true);


--
-- Data for Name: rnas; Type: TABLE DATA; Schema: annotation; Owner: genomes
--

COPY rnas (id, name, type, rna_name, sequence_id, x, y, strandness, gc_content, date_modified, date_created, parent_rna_id) FROM stdin;
\.


--
-- Name: rnas_id_seq; Type: SEQUENCE SET; Schema: annotation; Owner: genomes
--

SELECT pg_catalog.setval('rnas_id_seq', 837, true);


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

SELECT pg_catalog.setval('snps_id_seq', 8, true);


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

COPY chromosomes (id, number, species_genus, species_species, species_subspecies) FROM stdin;
1	1	Oryza	glaberrima	
10	10	Oryza	glaberrima	
11	11	Oryza	glaberrima	
12	12	Oryza	glaberrima	
2	2	Oryza	glaberrima	
3	3	Oryza	glaberrima	
4	4	Oryza	glaberrima	
5	5	Oryza	glaberrima	
6	6	Oryza	glaberrima	
7	7	Oryza	glaberrima	
8	8	Oryza	glaberrima	
9	9	Oryza	glaberrima	
\.


--
-- Name: chromosomes_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('chromosomes_id_seq', 29, true);


--
-- Data for Name: individuals; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY individuals (id, variety_name, description) FROM stdin;
\.


--
-- Name: individuals_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('individuals_id_seq', 3, true);


--
-- Data for Name: pseudomolecules; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY pseudomolecules (sequence_id, name, version, is_scaffold_derived, is_unplaced) FROM stdin;
\.


--
-- Data for Name: scaffolds; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY scaffolds (sequence_id, name, scaff_version, scaff_order, pseudomolecule_id, is_unplaced) FROM stdin;
\.


--
-- Data for Name: sequences; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY sequences (id, type, sequence, length, chromosome_id, superseded_by, date_modified, date_created) FROM stdin;
\.


--
-- Name: sequences_id_seq; Type: SEQUENCE SET; Schema: sequence; Owner: genomes
--

SELECT pg_catalog.setval('sequences_id_seq', 5361, true);


--
-- Data for Name: species; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY species (genus, species, subspecies, common_name, genome_type) FROM stdin;
Carthamus	tinctorius		safflower	\N
Caulerpa	taxifolia		alga, multinucleate	\N
Acyrthosiphon	pisum	pisum	pea aphid	\N
Centaurea	maculosa		spotted knapweed	\N
Centaurea	solstitialis		yellow star thistle	\N
Chara	sp.		alga, with sexual cells	\N
Chenopodium	quinoa		quinoa	\N
Chlamydomonas	reinhardtii		chlamydomonas	\N
Cichorium	endivia		endive	\N
Antirrhinum	majus	majus	snapdragon	\N
Cichorium	intybus		chicory	\N
Citrus	aurantium		citrus	\N
Citrus	reticulata		citrus	\N
Asd	asd	asd	e43c10	\N
Citrus	sinensis		citrus	\N
Clostridium	bifermantans		clostridium	\N
Coffea	arabica		coffee	\N
Coffea	canephora		coffee	\N
Coleochaete	orbicularis		alga, multicellular	\N
Manihot	esculenta		cassava	\N
Bemisia	tabaci	b-biotype	white fly	\N
Coleochaete	orbicularus		alga, multicellular	\N
Cucumis	melo		melon	\N
Cucumis	sativus		cucumber	\N
Curcuma	longa		turmeric	\N
Cynara	scolymus		artichoke	\N
Daucus	carota		\N	\N
Brassica	rapa	trilocularis	\N	\N
Cajanus	cajan	cajan	\N	\N
Draba	nivalis		\N	\N
Camellia	sinensis	bohea	tea	\N
Drosophila	albomicans		fruit fly	\N
Capsicum	frutescens	frutescens	pepper	\N
Drosophila	americana		fruit fly	\N
Drosophila	ananassae		fruit fly	\N
Drosophila	arizonae		fruit fly	\N
Drosophila	busckii		fruit fly	\N
Drosophila	erecta		fruit fly	\N
Drosophila	grimshawi		fruit fly	\N
Drosophila	hydei		fruit fly	\N
Drosophila	littoralis		fruit fly	\N
Chenopodium	quinoa	quinoa	quinoa	\N
Drosophila	mercatorum		fruit fly	\N
Chlamydomonas	reinhardtii	137c	chlamydomonas	\N
Chlamydomonas	reinhardtii	cw92 mt+	chlamydomonas	\N
Cicer	arietinum	arietinum	\N	\N
Drosophila	mimica		fruit fly	\N
Drosophila	mojavensis		fruit fly	\N
Drosophila	novamexicana		fruit fly	\N
Drosophila	persimilis		fruit fly	\N
Drosophila	repleta		fruit fly	\N
Drosophila	sechellia		fruit fly	\N
Drosophila	simulans		fruit fly	\N
Drosophila	virilis		fruit fly	\N
Coffea	eugeniodes	oficina	\N	\N
Drosophila	willistoni		fruit fly	\N
Drosophila	yakuba		fruit fly	\N
Eucalyptus	grandis		eucalyptus	\N
Ficus	citrifolia		fig	\N
Ficus	popenoei		fig	\N
Gallus	gallus		chicken	\N
Gerbera	hybrida		gerbera	\N
Ginglymostoma	cirratum		shark	\N
Gluconacetobacter	diazotrophicus		glucana	\N
Glycine	canescens		\N	\N
Glycine	cyrtoloba		\N	\N
Glycine	dolichocarpa		\N	\N
Glycine	falcata		\N	\N
Glycine	max		soybean	\N
Glycine	stenophita		\N	\N
Glycine	syndetika		soybean	\N
Glycine	tomentella		\N	\N
Gossypioides	kirkii		cotton	\N
Gossypium	arboreum		cotton	\N
Gossypium	exiguum		cotton	\N
Gossypium	herbaceum		cotton	\N
Gossypium	hirsutum		cotton	\N
Gossypium	raimondii		cotton	\N
Guizotia	abyssinica		\N	\N
Hamiltonella	defensa		bacterium	\N
Helianthus	annuus		sunflower	\N
Helianthus	argophyllus		silverleaf sunflower	\N
Helianthus	ciliaris		texas blueweed	\N
Helianthus	exilis		serpentine sunflower	\N
Euphorbia	esula	esula	\N	\N
Helianthus	paradoxus		paradox sunflower	\N
Ornithorhynchus	anatinus		platypus	\N
Oryza	alta		rice	\N
Oryza	australiensis		rice	\N
Oryza	barthii		rice	\N
Oryza	brachyantha		rice	\N
Oryza	coarctata		rice	\N
Oryza	eichingeri		\N	\N
Oryza	glumaepatula		rice	\N
Oryza	grandiglumis		\N	\N
Oryza	granulata		rice	\N
Glycine	max	max	\N	\N
Glycine	soja	soja	soybean	\N
Oryza	latifolia		rice	\N
Oryza	longistaminata		rice	\N
Oryza	meridionalis		rice	\N
Oryza	meyeriana		rice	\N
Oryza	minuta		rice	\N
Oryza	nivara		rice	\N
Oryza	officinalis		rice	\N
Oryza	punctata		rice	\N
Oryza	ridleyi		rice	\N
Oryza	rufipogon		rice	\N
Oryza	schlechteri		rice	\N
Parasponia	andersonii		parasponia	\N
Pegoscapus	estherae		fig wasp	\N
Pennisetum	ciliare		buffelgrass	\N
Petunia	hybrida		petunia	\N
Phalaenopsis	equestris		orchid	\N
Phaseolus	lunatus		bean	\N
Phaseolus	vulgaris		bean	\N
Poncirus	trifoliata		citrus	\N
Populus	deltoides		poplar	\N
Homo	sapiens	sapiens	human	\N
Populus	trichocarpa		poplar	\N
Hordeum	vulgare	spontaneum	barley	\N
Hordeum	vulgare	vulgare	barley	\N
Prunus	persica		peach	\N
Raphanus	sativus		radish	\N
Rattus	norveigicus		rat	\N
Rhodoferax	antarcticus		bacteria	\N
Rhodopila	globiformis		bacteria	\N
Saccharum	sp.		sugarcane	\N
Salmonella	typhimurium		salmonella	\N
Selaginella	moellendorfii		shining club moss	\N
Solanum	bulbocastanum		potato	\N
Solanum	demissum		potato	\N
Solanum	melongena		eggplant	\N
Solanum	tuberosum		potato	\N
Sorghum	bicolor		sorghum	\N
Sorghum	propinquum		sorghum	\N
Spinacia	oleracea		spinach	\N
Tachyglossus	aculeatus		echidna	\N
Taeniopygia	guttata		zebra finch	\N
Taraxacum	officinale		dandelion	\N
Thellungiella	halophila		salt cress	\N
Theobroma	cacao		\N	\N
Thermobia	domestica		firebrat	\N
Trifolium	pratense		clover	\N
Trifolium	repens		clover	\N
Triticum	aestivum		wheat	\N
Triticum	monococcum		wheat	\N
Ulmus	campestris		elm	\N
Nectria	haematococca	mpvi	fusarium	\N
Vigna	unguiculata		cowpea	\N
Vitis	vinifera		grape	\N
Volvox	carteri		\N	\N
Xenylla	pseudomaritima		springtail	\N
Zea	mays		maize	\N
Zingiber	officinale		ginger	\N
Zizania	palustris		rice	\N
Oryza	glaberrima		rice	AA
Oryza	sativa	indica	rice	\N
Oryza	sativa	japonica	rice	\N
Oryza	sativa	sativa	red rice	\N
Oryza	sativa l	indica	rice	\N
Oryza	sative	japonica	rice	\N
Persea	americana	drymifolia	avocado	\N
Phaseolus	vulgaris	mesoamerican	bean	\N
Phaseolus	vulgaris	mexicanus	bean	\N
Pyrenophora	teres	teres	fungus	\N
Saccharum	hybrid	cultivar(offic	sugarcane	\N
Triticum	aestivum	aestivum	wheat	\N
Vigna	unguiculata	unguiculata	cowpea	\N
Volvox	carteri	nagariensis	alga, colonial	\N
Yarrowia	lipolytica	e150	yarrowia	\N
Zea	mays	mays	maize	\N
Acorus	americanus		sweet flag	\N
Acorus	gramineus		\N	\N
Amaranthus	hypochondriacus		amaranth	\N
Amborella	trichocarpa		amborella	\N
Amborella	trichopoda		amborella	\N
Amborella	tricopoda		\N	\N
Angiopteris	evecta		angiopteris	\N
Anthoceros	formosae		hornwort	\N
Apis	mellifera		bee	\N
Apus	mellifera		bee	\N
Arabidopsis	thaliana		arabidopsis	\N
Ashbya	gossypii		ashbya	\N
Asparagus	officinalis		asparagus	\N
Asparagus	plumosus		asparagus	\N
Aspergillus	flavus		aspergillus	\N
Aspergillus	nidulans		aspergillus	\N
Barnadesia	spinosa		\N	\N
Biomphalaria	glabrata		snail	\N
Blumeria	graminis		fungus	\N
Boechera	holboellii		\N	\N
Boechera	stricta		\N	\N
Brachypodium	distachyon		brachypodium	\N
Bradyrhizobium	japonicum		bradyrhizobium	\N
Callithrix	jacchus		common marmoset	\N
Capsicum	annum		pepper	\N
Cardamine	hirsuta		\N	\N
Carsonella	ruddii		bacterium	\N
Helianthus	petiolaris		prairie sunflower	\N
Helianthus	tuberosus		jerusalem artichoke	\N
Hieracium	caespitosum		hawkweed	\N
Homo	sapiens		human	\N
Hordeum	vulgare		barley	\N
Lactuca	perennis		lettuce	\N
Lactuca	saligna		lettuce	\N
Lactuca	sativa		lettuce	\N
Lactuca	serriola		lettuce	\N
Lactuca	virosa		lettuce	\N
Lama	camelid		lama	\N
Leersia	perrieri		rice	\N
Liriodendron	tulipifera		liriodendron	\N
Lolium	perenne		ryegrass, perennial	\N
Lycopersicon	cheezmanii		tomato	\N
Lycopersicon	esculentum		tomato	\N
Lycopersicon	hirsutum		tomato	\N
Lycopersicum	esculentum		tomato	\N
Lycopersicum	pennellii		tomato	\N
Macaca	mulatta		rhesus monkey	\N
Macropus	eugenii		wallaby	\N
Magnaporthe	grisea		rice blast fungus	\N
Marchantia	polymorpha		liverwort	\N
Marselia	minuta		\N	\N
Medicago	truncatula		barrel medic	\N
Mesostigma	viride		alga, unicellular	\N
Mus	musculus		mouse	\N
Mycobacterium	ulcerans		bacterium	\N
Nicotiana	benthamiana		nicotiana	\N
Nuphar	advena		waterlily	\N
Ochrobactrum	anthropi		ochrobactrum	\N
Ocimum	basilicum		basil	\N
\.


--
-- Data for Name: varieties; Type: TABLE DATA; Schema: sequence; Owner: genomes
--

COPY varieties (name, genus, species, subspecies, alias) FROM stdin;
IRGC96717	Oryza	glaberrima		CG14
IRGC100122	Oryza	barthii		\N
IRGC100931	Oryza	barthii		\N
IRGC100934	Oryza	barthii		\N
IRGC103895	Oryza	barthii		\N
IRGC104084	Oryza	barthii		\N
IRGC104119	Oryza	barthii		\N
IRGC105608	Oryza	barthii		\N
IRGC106234	Oryza	barthii		\N
CG14	Oryza	glaberrima		\N
IRGC103469	Oryza	glaberrima		\N
TOG5457	Oryza	glaberrima		\N
TOG5467	Oryza	glaberrima		\N
TOG5923	Oryza	glaberrima		\N
TOG5949	Oryza	glaberrima		\N
TOG7025	Oryza	glaberrima		\N
TOG7102	Oryza	glaberrima		\N
\.


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
    ADD CONSTRAINT repeats_classification_pkey PRIMARY KEY (rclass, subclass, rorder, superfamily, family);


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
-- Name: chromosomes_number_species_genus_species_species_species_su_key; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY chromosomes
    ADD CONSTRAINT chromosomes_number_species_genus_species_species_species_su_key UNIQUE (number, species_genus, species_species, species_subspecies);


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
-- Name: scaff_nat_key; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY scaffolds
    ADD CONSTRAINT scaff_nat_key UNIQUE (name, scaff_version);


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
-- Name: species_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY species
    ADD CONSTRAINT species_pkey PRIMARY KEY (genus, species, subspecies);


--
-- Name: unique_pseudom_name; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY pseudomolecules
    ADD CONSTRAINT unique_pseudom_name UNIQUE (name, version);


--
-- Name: varieties_pkey; Type: CONSTRAINT; Schema: sequence; Owner: genomes; Tablespace: 
--

ALTER TABLE ONLY varieties
    ADD CONSTRAINT varieties_pkey PRIMARY KEY (name);


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
-- Name: fk_repeat_class; Type: FK CONSTRAINT; Schema: annotation; Owner: genomes
--

ALTER TABLE ONLY repeats
    ADD CONSTRAINT fk_repeat_class FOREIGN KEY (repeats_class, repeats_subclass, repeats_order, repeats_superfamily, repeats_family) REFERENCES repeats_classification(rclass, subclass, rorder, superfamily, family) ON UPDATE CASCADE ON DELETE CASCADE;


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
-- Name: fk_chromo_species; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY chromosomes
    ADD CONSTRAINT fk_chromo_species FOREIGN KEY (species_genus, species_species, species_subspecies) REFERENCES species(genus, species, subspecies) ON UPDATE CASCADE ON DELETE CASCADE;


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
-- Name: individuals_variety_name_fkey; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY individuals
    ADD CONSTRAINT individuals_variety_name_fkey FOREIGN KEY (variety_name) REFERENCES varieties(name) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: scaffolds_pseudomolecule_id_fkey; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY scaffolds
    ADD CONSTRAINT scaffolds_pseudomolecule_id_fkey FOREIGN KEY (pseudomolecule_id) REFERENCES pseudomolecules(sequence_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: sequences_variety_name_fkey; Type: FK CONSTRAINT; Schema: sequence; Owner: genomes
--

ALTER TABLE ONLY varieties
    ADD CONSTRAINT sequences_variety_name_fkey FOREIGN KEY (genus, species, subspecies) REFERENCES species(genus, species, subspecies) ON UPDATE CASCADE ON DELETE CASCADE;


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

