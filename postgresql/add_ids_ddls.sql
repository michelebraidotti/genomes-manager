-- Adding id column autoincremet to species and varieties
-- upadting all references
-- WARNING: procedure has to be monitored, the "select" query generates info 
--          that has to be inserted manually

ALTER TABLE "sequence"."species" ADD COLUMN "id" INTEGER;
CREATE SEQUENCE "sequence"."species_id_seq";
UPDATE species SET id = nextval('"sequence"."species_id_seq"');
ALTER TABLE "sequence"."species"
  ALTER COLUMN id SET DEFAULT nextval('"sequence"."species_id_seq"');
ALTER TABLE "sequence"."species"
  ALTER COLUMN "id" SET NOT NULL;
ALTER TABLE "sequence"."species" ADD UNIQUE ("id");

alter table chromosomes DROP CONSTRAINT fk_chromo_species;
alter table varieties DROP CONSTRAINT sequences_variety_name_fkey;

ALTER TABLE "sequence"."species" DROP CONSTRAINT "species_pkey" RESTRICT;
ALTER TABLE "sequence"."species" ADD PRIMARY KEY ("id");
ALTER TABLE "sequence"."species" ADD UNIQUE ("species", "genus", "subspecies");

ALTER TABLE "sequence"."chromosomes" ADD COLUMN "species_id" INTEGER;

select 'UPDATE sequence.chromosomes SET species_id = ' || s.id || ' WHERE id = ' || c.id  || ';'from species s join chromosomes c on (s.genus = c.species_genus and s.species = c.species_species and s.subspecies = c.species_subspecies);

ALTER TABLE chromosomes DROP COLUMN species_genus;
ALTER TABLE chromosomes DROP COLUMN species_species;
ALTER TABLE chromosomes DROP COLUMN species_subspecies;
ALTER TABLE chromosomes ADD CONSTRAINT "fk_chromosomes_species" FOREIGN KEY (species_id) REFERENCES species(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "sequence"."varieties" ADD COLUMN "species_id" INTEGER;
select 'UPDATE sequence.varieties SET species_id = ' || s.id || ' WHERE name = ''' || v.name  || ''';' from species s join varieties v on (s.genus = v.genus and s.species = v.species and v.subspecies = s.subspecies);
ALTER TABLE varieties DROP COLUMN genus;
ALTER TABLE varieties DROP COLUMN species;
ALTER TABLE varieties DROP COLUMN subspecies;
ALTER TABLE varieties ADD CONSTRAINT "fk_varietiss_species" FOREIGN KEY (species_id) REFERENCES species(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "sequence"."varieties" ADD COLUMN "id" INTEGER;
CREATE SEQUENCE "sequence"."varieties_id_seq";
UPDATE varieties SET id = nextval('"sequence"."varieties_id_seq"');
ALTER TABLE "sequence"."varieties"
  ALTER COLUMN id SET DEFAULT nextval('"sequence"."varieties_id_seq"');
ALTER TABLE "sequence"."varieties"
  ALTER COLUMN "id" SET NOT NULL;
ALTER TABLE individuals DROP CONSTRAINT individuals_variety_name_fkey;
ALTER TABLE "sequence"."varieties" DROP CONSTRAINT "varieties_pkey" RESTRICT;
ALTER TABLE "sequence"."varieties" ADD PRIMARY KEY ("id");
ALTER TABLE "sequence"."varieties" ADD UNIQUE ("name");

ALTER TABLE "individuals" ADD COLUMN "variety_id" INTEGER;
select 'UPDATE sequence.individuals SET variety_id = ' || v.id || ' WHERE id = ' || i.id  || ';'  from varieties v join individuals i on (v.name = i.variety_name);
ALTER TABLE individuals DROP COLUMN variety_name;
ALTER TABLE individuals ADD CONSTRAINT "fk_individuals_varieties" FOREIGN KEY (variety_id) REFERENCES varieties(id) ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "annotation"."repeats_classification" ADD COLUMN "id" INTEGER;
CREATE SEQUENCE "annotation"."repeats_classification_id_seq";
UPDATE repeats_classification SET id = nextval('"annotation"."repeats_classification_id_seq"');
ALTER TABLE "annotation"."repeats_classification" ALTER COLUMN id SET DEFAULT nextval('"annotation"."repeats_classification_id_seq"');
ALTER TABLE "annotation"."repeats" DROP CONSTRAINT "fk_repeat_class";
ALTER TABLE "annotation"."repeats_classification" DROP CONSTRAINT repeats_classification_pkey RESTRICT;
ALTER TABLE "annotation"."repeats_classification" ADD PRIMARY KEY ("id");
ALTER TABLE "annotation"."repeats_classification" ADD UNIQUE (rclass, subclass, rorder, superfamily, family);
ALTER TABLE "annotation"."repeats" ADD COLUMN "repeats_classification_id" INTEGER;

ALTER TABLE "annotation"."repeats" ADD COLUMN "repeats_classification_id" INTEGER;
select 'UPDATE annotation.repeats SET repeats_classification_id = ' || rc.id || ' WHERE id = ' || r.id  || ';' from repeats r join repeats_classification rc on (r.repeats_class = rc.rclass and r.repeats_subclass = rc.subclass and r.repeats_order = rc.rorder and r.repeats_superfamily = rc.superfamily and r.repeats_family = rc.family )\o update_repeats.sql;
ALTER TABLE repeats DROP COLUMN repeats_class; 
ALTER TABLE repeats DROP COLUMN repeats_subclass; 
ALTER TABLE repeats DROP COLUMN repeats_order; 
ALTER TABLE repeats DROP COLUMN repeats_superfamily; 
ALTER TABLE repeats DROP COLUMN repeats_family;
ALTER TABLE repeats ADD CONSTRAINT "fk_repeats_repeats_classification" FOREIGN KEY (repeats_classification_id) REFERENCES repeats_classification(id) ON UPDATE CASCADE ON DELETE SET NULL;

