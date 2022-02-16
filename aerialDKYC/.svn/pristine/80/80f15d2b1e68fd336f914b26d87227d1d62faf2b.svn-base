package com.edios.cdf.util;

import java.util.Collections;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.persister.entity.AbstractEntityPersister;

public class DbCustomFunctions {

	  public String toSql(Session session, String hqlQueryText){
		  
	       if (hqlQueryText!=null && hqlQueryText.trim().length()>0){
	         final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
	         final SessionFactoryImplementor factory = (SessionFactoryImplementor) session.getSessionFactory();
	         final QueryTranslator translator = translatorFactory.createQueryTranslator(
	             hqlQueryText, 
	             hqlQueryText, 
	             java.util.Collections.EMPTY_MAP, factory,null
	           );
	         translator.compile(Collections.EMPTY_MAP, false);
	         return translator.getSQLString(); 
	       }
	       return null;
	     }	
	  
	  
	  public String getTableName(Class entity) {
		  Table table = (Table)entity.getAnnotation(Table.class);
		  return table.name().toUpperCase();
	  }
	  
	  public String getPkColumnName(Class entity,Session session) {
			MetamodelImplementor metamodel = (MetamodelImplementor) session.getSessionFactory().getMetamodel();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister)((ClassMetadata) metamodel.entityPersister(entity));
			String[] columns=classMetadata.getIdentifierColumnNames();
			return  columns[0];
			
//			AbstractEntityPersister aep=((AbstractEntityPersister)session.getSessionFactory().getClassMetadata(PharmacyStoreEntity.class));  
//			String[] columns=aep.getIdentifierColumnNames();
//			String pkColName = columns[0];
//	
	  }
	  
	  public String getpkIndentifierName(Class entity,Session session) {
		  MetamodelImplementor metamodel = (MetamodelImplementor) session.getSessionFactory().getMetamodel();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister)((ClassMetadata) metamodel.entityPersister(entity));
			return classMetadata.getIdentifierPropertyName();
			
			
//		  ClassMetadata employeeMeta =  session.getSessionFactory().getClassMetadata(entity);
//		  return employeeMeta .getIdentifierPropertyName();
	  }
	  
	
}
