package com.chenjw.spider.dt.service;

import java.util.List;

import com.chenjw.spider.dt.model.TweetModel;
import com.chenjw.spider.dt.utils.Page;
import com.chenjw.spider.dt.utils.PagedResult;

public interface DeletedTweetReadService {

	public PagedResult<TweetModel> findDeletedTweetsByUserId(String userId,
			Page page);

	public int countDeletedTweetsByUserId(String userId, Page page);

	public List<TweetModel> findTopReposts();

}
