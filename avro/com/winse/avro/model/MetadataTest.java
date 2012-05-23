package com.winse.avro.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class MetadataTest {
	
	@Test
	public void testLoadSchema() throws Exception {
		Schema.Parser parser = new Schema.Parser();
		/**
		 * see org.apache.avro.Schema.Parser.names # 已解析的模型缓冲
		 * see org.apache.avro.Schema.parse(JsonNode, Names) #1082 #1124 #1140 ##修改names MAP
		 */
		// 首先解析依赖，会把解析好的模型放置到 {@linked Parser#names} Map中.
		parser.parse(getClass().getResourceAsStream("Lucene_Document.avsc"));
		Schema schema = parser.parse(getClass().getResourceAsStream("LuceneModel.avsc"));
		System.out.println(schema.toString(true));

		List<Field> fields = schema.getFields();
		System.out.println(ArrayUtils.toString(fields));
		
		testSimpleInheritance(schema);
	}

	public static void testSimpleInheritance(Schema schema) throws Exception {
		// 写数据
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

		List<Field> fields = schema.getFields();
		Schema subSchema = fields.get(0).schema();

		// 
		GenericRecord doc_id_1 = new GenericData.Record(subSchema);
		doc_id_1.put("caption", new Utf8("id:Who1"));
		doc_id_1.put("description", new Utf8("id Who1"));
		
		GenericRecord doc_domain_1 = new GenericData.Record(subSchema);
		doc_domain_1.put("caption", new Utf8("domain:Who1"));
		doc_domain_1.put("description", new Utf8("domain Who1"));
		
		GenericRecord record = new GenericData.Record(schema);
		record.put("id", doc_id_1);
		record.put("domain", doc_domain_1);
		writer.write(record, encoder);

		// 
		GenericRecord doc_id_2 = new GenericData.Record(subSchema);
		doc_id_2.put("caption", new Utf8("id:Who2"));
		doc_id_2.put("description", new Utf8("id Who2"));
		
		GenericRecord doc_domain_2 = new GenericData.Record(subSchema);
		doc_domain_2.put("caption", new Utf8("domain:Who2"));
		doc_domain_2.put("description", new Utf8("domain Who2"));
		
		GenericRecord record2 = new GenericData.Record(schema);
		record2.put("id", doc_id_2);
		record2.put("domain", doc_domain_2);
		writer.write(record2, encoder);

		encoder.flush();

		// 读数据
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
		DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
		while (true) {
			try {
				GenericRecord result = reader.read(null, decoder);
				System.out.println(result);
			} catch (EOFException eof) {
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		/*
{"id": {"caption": "id:Who1", "description": "id Who1"}, "domain": {"caption": "domain:Who1", "description": "domain Who1"}}
{"id": {"caption": "id:Who2", "description": "id Who2"}, "domain": {"caption": "domain:Who2", "description": "domain Who2"}}
		 */
	}

	/*
	{
	  "type" : "record",
	  "name" : "bgjdLuceneModel",
	  "namespace" : "o.a.lucene.meta",
	  "doc" : "bgjd Lucene Model",
	  "fields" : [ {
	    "name" : "id",
	    "type" : {
	      "type" : "record",
	      "name" : "Document",
	      "doc" : "Document analysis Desc",
	      "fields" : [ {
	        "name" : "caption",
	        "type" : "string"
	      }, {
	        "name" : "description",
	        "type" : "string"
	      } ]
	    }
	  }, {
	    "name" : "domain",
	    "type" : "Document"
	  } ]
	}		
	 */
	
}
