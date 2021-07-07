package www.dream.com.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;

@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttachMapperTest {
   @Autowired
   private AttachFileVOMapper attachMapper;

   @Test
   public void test200Insert() {
	   String postId = "TTTT1";
	   List<AttachFileVO> list = new ArrayList<>();
	   AttachFileVO avo = new AttachFileVO();
	   avo.setUuid(UUID.randomUUID().toString());
	   avo.setSavedFolderPath("retui");
	   avo.setPureFileName("rtfyguhui");
	   avo.setMultimediaType(MultimediaType.image);
	   list.add(avo);
	
	   avo = new AttachFileVO();
	   avo.setUuid(UUID.randomUUID().toString());
	   avo.setSavedFolderPath("retui");
	   avo.setPureFileName("rtfadsfayguhui");
	   avo.setMultimediaType(MultimediaType.video);
	   list.add(avo);
	   
	   attachMapper.insert(postId, list);

   }
   
   @Test
   public void test400Delete() {
	String postId = "TTTT0";
	attachMapper.delete(postId);
	
   }

}