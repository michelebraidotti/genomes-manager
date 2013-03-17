package org.genomesmanager.patches

import groovy.sql.Sql

class SampleScript {

	static main(args) {
		def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/genomes", "genomes", "12345", 
			"org.hibernate.dialect.PostgreSQLDialect")
		//def name = sql.firstRow("select * from Species").common_name; 
		//println "Hello world, ${name}"
		sql.withTransaction {
			// do something within a transaction
		}
	}
}

