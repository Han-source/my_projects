package www.dream.com.bulletinBoard.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.hashTag.service.HashTagService;
import www.dream.com.party.model.Party;

/**
 * 이는 ReplyVO와 PostVO의 클래스 설계도를 기반으로 해당 Table을 Top 전략으로 통합하여 만들었기에 ReplyMapper는
 * 통합 PostService를 ReplyService와 분리
 * 
 * @author j222_sang
 *
 */
@Service
public class PostService {

	@Autowired
	private ReplyMapper replyMapper;

	@Autowired
	private HashTagService hashtagService;

	@Autowired
	private AttachFileVOMapper attachFileVOMapper;

	// LRCUD
	/** 페이지가 몇개 있는지 를 실행해주는 함수 */
	public long getSearchTotalCount(int boardId, Criteria cri) {
		// 검색어가 있는지 조건 처리
		if (cri.hasSearching()) {
			// 검색어가 있다면 getSearchTotalCount함수를 실행
			return replyMapper.getSearchTotalCount(boardId, cri);
		} else {
			return replyMapper.getTotalCount(boardId, PostVO.DESCRIM4POST);
		}
	}

	/* 리스트를 띄워주는 역할 */
	public List<PostVO> getListByHashTag(Party curUser, int boardId, Criteria cri) {
		if (cri.hasSearching()) {
			String[] searchingHashtags = cri.getSearchingHashtags();
			if (curUser != null) {
				mngPersonalFavorite(curUser, searchingHashtags);
			}
			return replyMapper.getListByHashTag(boardId, cri);
		} else {
			return replyMapper.getList(boardId, cri);
		}
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return (PostVO) replyMapper.findReplyById(id);
	}

	/** id 값으로 Post 객체 조회한 게시글의 조회수 + 1 */
	public PostVO plusReadCnt(String id) {
		return (PostVO) replyMapper.plusReadCnt(id);
	}

	/**
	 * 
	 * @param board
	 * @param post  화면에서 사용자가 입력한 변수
	 * @return
	 */
	@Transactional
	public int insert(BoardVO board, PostVO post) {
		// 영향받은 행 개수 : 몇건을 처리를 했는가
		int affectedRows = replyMapper.insert(board, post);
		// mapOccur : 단어의 출현빈도구하기,
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post);
		// 출현빈도를 바탕으로 단어만 추출
		
		// 단어가 비어있지 않다면
		hashtagService.createHashTagAndMapping(post, mapOccur);

		// 첨부 파일 정보도 관리. 고성능
		List<AttachFileVO> listAttach = post.getListAttach();
		if (listAttach != null && !listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		return affectedRows;
	}


	/** 게시글 수정 처리 
	 * 첨부 파일 정보 기존 것 다 없애고 다시 등록
	 * 연관 단어도 기존 것 다 없애고 다시 등록
	 */
	@Transactional
	public boolean updatePost(PostVO post) {
		attachFileVOMapper.delete(post.getId());
		// 첨부 파일 정보도 관리. 고성능
		List<AttachFileVO> listAttach = post.getListAttach();
		if (listAttach != null && !listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		hashtagService.deleteMap(post);
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post);
		hashtagService.createHashTagAndMapping(post, mapOccur);
		
		return replyMapper.updatePost(post) == 1;
	}

	/** id 값으로 Post 객체 삭제 */
	@Transactional
	public boolean deletePostById(String id) {
		PostVO post = new PostVO();
		post.setId(id);
		hashtagService.deleteMap(post);
		attachFileVOMapper.delete(id);
		return replyMapper.deleteReplyById(id) == 1;
	}

	private void mngPersonalFavorite(Party curUser, String[] searchingHashtags) {
		Map<String, Integer> mapOccur = new HashMap<String, Integer>();
		Arrays.stream(searchingHashtags).forEach(word -> {
			mapOccur.put(word, 1);
		});
		hashtagService.mngHashTagAndMapping(curUser, mapOccur);		
		//신규 단어 등록 및 활용 횟수는 1
		hashtagService.createHashTagAndMapping(curUser, mapOccur);
	}
	
}