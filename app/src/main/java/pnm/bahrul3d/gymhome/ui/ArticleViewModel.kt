package pnm.bahrul3d.gymhome.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import pnm.bahrul3d.gymhome.data.ArticleRepository
import pnm.bahrul3d.gymhome.model.Article

class ArticleViewModel : ViewModel() {

    private val repository = ArticleRepository()

    val articles: StateFlow<List<Article>>
            = repository.articles

    fun addArticle(
        title: String,
        content: String,
        author: String
    ) {

        repository.addArticle(
            Article(
                title,
                content,
                author
            )
        )
    }
}