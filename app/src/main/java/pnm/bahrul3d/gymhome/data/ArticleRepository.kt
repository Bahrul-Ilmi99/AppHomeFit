package pnm.bahrul3d.gymhome.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pnm.bahrul3d.gymhome.model.Article

class ArticleRepository {

    private val _articles =
        MutableStateFlow<List<Article>>(emptyList())

    val articles: StateFlow<List<Article>>
        get() = _articles

    fun addArticle(article: Article) {

        _articles.value =
            _articles.value + article
    }
}