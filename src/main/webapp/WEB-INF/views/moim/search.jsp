<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>M.O.I.M</title>
<link rel="stylesheet" href="/resource/css/style.css?${millis }" />
</head>
<body>
	<%@include file="/WEB-INF/views/common/top.jsp" %>
		<c:set var="joinedCate" value="${fn:join(paramValues.cate, '-') }"/>
		<%-- 메인 / 검색창 및 검색 결과 뷰 --%>
		<div style="flex: 1; min-width: 95vw; margin-top: 20px" class="block">
			<form class="block-row" style="align-items: center;" action="/moim/search">
				<div style="flex: 1" class="block-row">
					<c:forTokens items="스포츠,문화,학습,봉사,사교,게임,기타" delims="," var="one"
						varStatus="vs">
						<div>
							<input name="cate" type="checkbox" value="${one }" id="cate${vs.count }" 
								id="cate${vs.count }"${fn: contains(joinedCate, one) ? 'checked' :''}/>
								<label for="cate${vs.count }">${one }</label>
						</div>
					</c:forTokens>
				</div>
				<div style="" class="block">
					<input type="text" style="flex: 1"  placeholder="유형을 검색해주세요."/>
				</div>
				<div style="" class="block">
					<button style="flex: 1" type="submit">검색</button>
				</div>
			</form>
			<div style="flex: 1" class="block-row" >
			<c:forEach items="${list }" var="moim" >
				<div class="moim-detail-card block" onclick="location.href='/moim/detail?id=${moim.id}'">
					<div>
						<span style="color: black">[${moim.type eq 'public' ? '공개' : '비공개' } / ${moim.cate }]</span>&nbsp;
						<c:choose>
							<c:when test="${fn:length(moim.event) gt 16}">
									${fn:substring(moim.event, 0,16) }...
								</c:when>
							<c:otherwise>
									${moim.event }
							</c:otherwise>
						</c:choose>
						(${moim.currentPerson }/${moim.maxPerson })
					</div>
					<div style="text-align: left">
						# 일정 : <span><fmt:formatDate value="${moim.beginDate }"
								pattern="yyyy.MM.dd HH:mm" /></span> ~ <span>
						<fmt:formatDate value="${moim.endDate }" pattern="HH:mm" /></span>

					</div>
					<div style="text-align: left">
						# 회비 : <span> <fmt:formatNumber value="${moim.finalCost }"
								pattern="#,###" />
						</span>
					</div>
					<div style="text-align: left">
						# 소개 : <span>${moim.description }</span>
					</div>
				</div>
	
				</c:forEach>
				</div>
			</div>
</body>
</html>