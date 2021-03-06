package com.hao.easy.wan.viewmodel

import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api
import com.mvvm.aop.CheckLogin
import com.mvvm.http.subscribeBy
import com.mvvm.viewmodel.BaseListViewModel

abstract class BaseArticleViewModel : BaseListViewModel<Article>() {

    @CheckLogin
    fun collect(item: Article, position: Int) {
        if (item.collect) {
            doCancelCollect(item, position)
        } else {
            doCollect(item, position)
        }
    }

    private fun doCollect(item: Article, position: Int) {
        Api.collect(item.id).subscribeBy({
            item.collect = true
            notifyItem(position, "fav")
        }).add()
    }

    open fun doCancelCollect(item: Article, position: Int) {
        Api.cancelCollect(item.id).subscribeBy({
            item.collect = false
            notifyItem(position, "fav")
        }).add()
    }
}