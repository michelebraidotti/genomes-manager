ALTER TABLE "sequence"."sequences" ADD COLUMN name VARCHAR(55)
ALTER TABLE "sequence"."sequences" ADD COLUMN version VARCHAR(9)
SELECT 'UPDATE sequence.sequences SET name = ''' || sc.name || ''', version = ''' || sc.scaff_version || ''' WHERE id = ' || s.id || ';' FROM sequences s join scaffolds sc on s.id = sc.sequence_id;
SELECT 'UPDATE sequence.sequences SET name = ''' || p.name || ''', version = ''' || p.version || ''' WHERE id = ' || s.id || ';' FROM sequences s join pseudomolecules p on s.id = p.sequence_id;
ALTER TABLE scaffolds DROP COLUMN name;
ALTER TABLE scaffolds DROP COLUMN scaff_version;
ALTER TABLE pseudomolecules DROP COLUMN name;
ALTER TABLE pseudomolecules DROP COLUMN version;

