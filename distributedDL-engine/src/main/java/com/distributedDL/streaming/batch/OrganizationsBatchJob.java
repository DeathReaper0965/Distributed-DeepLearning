package com.distributedDL.streaming.batch;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.types.Row;

import com.distributedDL.streaming.batch.cache.mapping.OrgBatchCacheMapping;
import com.distributedDL.streaming.cachedb.RocksDbCache;
import com.distributedDL.streaming.common.CommonConstants;

public class OrganizationsBatchJob extends BatchJob{
	
	public OrganizationsBatchJob() {
		super(CommonConstants.ORGANIZATIONS_TABLE, CommonConstants.MONGO_DUMP_DB, null);
	}
	
	public ExecutionEnvironment startbatch() {
		
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.DATE_TYPE_INFO,
													BasicTypeInfo.DATE_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.LONG_TYPE_INFO,
													BasicTypeInfo.BOOLEAN_TYPE_INFO,
													BasicTypeInfo.BOOLEAN_TYPE_INFO,
													BasicTypeInfo.BOOLEAN_TYPE_INFO,
													BasicTypeInfo.BOOLEAN_TYPE_INFO,
													BasicTypeInfo.DATE_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.DOUBLE_TYPE_INFO,
													BasicTypeInfo.DOUBLE_TYPE_INFO,
													BasicTypeInfo.STRING_TYPE_INFO,
													BasicTypeInfo.DATE_TYPE_INFO
										        };
	        
		JDBCInputFormat organizationInputFormat = this.getInputFormat(fieldTypes);
		
		RocksDbCache rocksDbCache = this.setCacheAndGetRocksDB(CommonConstants.ORGANIZATIONS_CACHE);
		
		DataSet<Row> organizationsDataSet = this.getExecutionEnvironment().createInput(organizationInputFormat).setParallelism(10);
		
		DataSet<Boolean> statusDataSet = organizationsDataSet.map(new OrgBatchCacheMapping(rocksDbCache));
		
		long elemCount = 0;
		
		try {
			elemCount = statusDataSet.count();
			
			System.out.println(String.format("Successfully inserted %s values to RocksDB", elemCount));
			
			return this.getExecutionEnvironment();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
