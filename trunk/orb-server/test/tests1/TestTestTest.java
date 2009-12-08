package tests1;

import com.curlap.orb.security.RemoteService;
import com.curlap.orb.type.DataTypeException;
import com.curlap.orb.type.SerializableRecordData;
import com.curlap.orb.type.SerializableRecordField;
import com.curlap.orb.type.SerializableRecordSet;

@RemoteService
public class TestTestTest {
	public TestTestTest() {
		
	}
	
	public SerializableRecordSet getShopMaster(String chkStr,String costCentreName){

		//ここにDBと連携する処理を実装すると、サーバのデータを返すことが出来ます。


		//クライアントに返却するデータを定義します。
		SerializableRecordSet records = new SerializableRecordSet(
				new SerializableRecordField[]{
						new SerializableRecordField("CmpCode", String.class),
						new SerializableRecordField("CSICentreCode", String.class),
						new SerializableRecordField("CSICentreName", String.class),
						new SerializableRecordField("CostCentreCode", String.class),
						new SerializableRecordField("CostCentreName", String.class)
				}
		);


		//データを
		try {
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530110", "栃木（共通）"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530111", "栃木（流通小売第1）"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530114", "栃木（ﾌｰﾄﾞｲﾝﾀﾞｽﾄﾘｰ）"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530115", "栃木（ﾛｼﾞｽﾃｨｯｸｽ）"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530116", "栃木（ﾎｽﾋﾟﾀﾘﾃｨ）"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "栃木", "530118", "栃木（ﾌｨｰﾙﾄﾞｻｰﾋﾞｽ）"}
					)
			);
		} catch (DataTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return records;
	}

}
