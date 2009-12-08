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

		//������DB�ƘA�g���鏈������������ƁA�T�[�o�̃f�[�^��Ԃ����Ƃ��o���܂��B


		//�N���C�A���g�ɕԋp����f�[�^���`���܂��B
		SerializableRecordSet records = new SerializableRecordSet(
				new SerializableRecordField[]{
						new SerializableRecordField("CmpCode", String.class),
						new SerializableRecordField("CSICentreCode", String.class),
						new SerializableRecordField("CSICentreName", String.class),
						new SerializableRecordField("CostCentreCode", String.class),
						new SerializableRecordField("CostCentreName", String.class)
				}
		);


		//�f�[�^��
		try {
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530110", "�Ȗ؁i���ʁj"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530111", "�Ȗ؁i���ʏ�����1�j"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530114", "�Ȗ؁ḭ�޲��޽�ذ�j"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530115", "�Ȗ؁iۼ޽è����j"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530116", "�Ȗ؁iν����è�j"}
					)
			);
			records.addRecord(
					new SerializableRecordData(
							new String[]{"CmpCode", "CSICentreCode", "CSICentreName", "CostCentreCode", "CostCentreName"},
							new Object[]{"100", "53011", "�Ȗ�", "530118", "�Ȗ؁į���޻��޽�j"}
					)
			);
		} catch (DataTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return records;
	}

}
