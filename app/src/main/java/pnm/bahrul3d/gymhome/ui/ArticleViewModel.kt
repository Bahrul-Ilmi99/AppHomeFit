package pnm.bahrul3d.gymhome.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pnm.bahrul3d.gymhome.model.Article

class ArticleViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        db.collection("articles")
            .orderBy("id", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                _articles.value = snapshot?.toObjects(Article::class.java) ?: emptyList()
            }
    }

    fun addArticle(title: String, content: String, author: String, authorId: String) {
        val id = db.collection("articles").document().id
        val article = Article(id, title, content, author, authorId)
        db.collection("articles").document(id).set(article)
    }

    fun deleteArticle(id: String) {
        db.collection("articles").document(id).delete()
    }
}
