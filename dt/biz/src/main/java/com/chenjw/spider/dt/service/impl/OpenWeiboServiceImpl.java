package com.chenjw.spider.dt.service.impl;

import java.util.ArrayList;
import java.util.List;

import weibo4j.Comments;
import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.chenjw.spider.dt.model.TweetModel;
import com.chenjw.spider.dt.model.UserModel;
import com.chenjw.spider.dt.model.UserTokenModel;
import com.chenjw.spider.dt.service.Handler;
import com.chenjw.spider.dt.service.WeiboService;
import com.chenjw.spider.dt.utils.OAuthUtils;
import com.chenjw.tools.BeanCopyUtils;

public class OpenWeiboServiceImpl implements WeiboService {
	// private String token = "2.00WaGSGC0TXPFW2859bb150etwDzcB";

	private String token = "2.006ONAWD0TXPFWdf8b2ae70b00kK9K";
	private Timeline timelineManager = new Timeline();
	private Comments commentsManager = new Comments();
	private Users usersManager = new Users();
	private Friendships friendshipsManager = new Friendships();
	String signedRequest = "85MbyIjVIPyoIXd0AwQVs0nqwAEQPvKyM2QdvDjxU94.eyJ1c2VyIjp7ImNvdW50cnkiOiJjbiIsImxvY2FsZSI6IiIsInZlcnNpb24iOjV9LCJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImlzc3VlZF9hdCI6MTM1Nzk3MzYwMywicmVmZXJlciI6Imh0dHA6XC9cL3dlaWJvLmNvbVwvbG9naW4ucGhwP3VybD1odHRwJTNBJTJGJTJGYXBwcy53ZWliby5jb20lMkZjaGVuand0ZXN0In0";
	{
		System.out.println(OAuthUtils.getAuthorizeUrl());
		System.out.println(OAuthUtils
				.getAccessTokenByCode("770f9bea368f6c9d69e1ca619ae3d821"));
		timelineManager.setToken(token);
		commentsManager.setToken(token);
		usersManager.setToken(token);
		friendshipsManager.setToken(token);

	}

	public String[] findFriendIdsByUserId(String userId) {
		try {
			return friendshipsManager.getFriendsIdsByUid(userId, 5000, 0);
		} catch (WeiboException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 潇潇coffelife

	private void readStatuses(String uid, long sinceId, Handler handler)
			throws WeiboException {
		long lastId = 0;
		while (true) {
			Paging page = new Paging();
			if (lastId != 0) {
				page.setMaxId(lastId);
			}
			if (sinceId != 0) {
				// 因为需要包括这一条，所以必须-1
				page.setSinceId(sinceId - 1);
			}

			page.setCount(100);
			StatusWapper status;
			status = timelineManager.getUserTimelineByUid(uid, page, 0, 0);
			if (status.getStatuses().size() == 0) {
				return;
			}
			for (Status s : status.getStatuses()) {
				handler.handleStatus(s);
				lastId = s.getIdstr() - 1;
			}
			// 如果没到count数量，表示只有一页，退出
			if (status.getStatuses().size() < page.getCount()) {
				return;
			}

		}

	}

	@Override
	public String findUserIdByName(String name) {
		User user;
		try {
			user = usersManager.showUserByScreenName(name);
		} catch (WeiboException e) {
			e.printStackTrace();
			return null;
		}
		return user.getId();
	}

	@Override
	public List<TweetModel> findUserTimelineByUserId(final String userId,
			long sinceId) {

		final List<TweetModel> result = new ArrayList<TweetModel>();
		Handler handler = new Handler() {
			@Override
			public void handleStatus(Status s) {
				TweetModel model = new TweetModel();
				BeanCopyUtils.copyProperties(model, s);
				UserModel user = new UserModel();
				BeanCopyUtils.copyProperties(user, s.getUser());
				model.setUser(user);
				result.add(model);
			}
		};
		// 目标用户
		try {
			readStatuses(userId, sinceId, handler);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<TweetModel> findFriendsTimelineByUserId(UserTokenModel user,
			long sinceId) {
		// TODO Auto-generated method stub
		return null;
	}

}