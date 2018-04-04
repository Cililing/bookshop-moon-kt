package com.example.t_rex.moonbookshop.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.basket.BasketService
import com.example.t_rex.moonbookshop.basket.IBasketService
import com.example.t_rex.moonbookshop.data.AppDatabase
import com.example.t_rex.moonbookshop.data.BookModel
import com.example.t_rex.moonbookshop.data.CategoryModel
import com.example.t_rex.moonbookshop.fragments.BasketFragment
import com.example.t_rex.moonbookshop.fragments.BookListFragment
import com.example.t_rex.moonbookshop.fragments.CategoriesFragment
import com.example.t_rex.moonbookshop.fragments.MainFragment
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    private val mKodein = LazyKodein(appKodein)
    private val mBasketService: IBasketService by lazy { appKodein().instance<IBasketService>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add nav listener
        this.nav_view.setNavigationItemSelectedListener { item -> loadHomeFragment(item.itemId) }

        // Add appBarButtonListener
        this.toolbarBasket.setOnClickListener { _ -> changeFragment(BasketFragment.newInstance())}

        // Validate necessary views
        this.validateBasketButton()
    }

    fun changeFragment(fragment: Fragment) {
        applyFragmentTransaction(fragment)
    }

    private fun getFragment(navItemId: Int) : Fragment {
        return when (navItemId) {
            R.id.home -> MainFragment.newInstance()
            R.id.categories -> CategoriesFragment.newInstance()
            R.id.loadData -> {
                loadData(mKodein().instance())
                MainFragment.newInstance()
            }
            R.id.bestsellers -> {
                BookListFragment.newInstance(BookListFragment.Companion.BookListViewParams(BookListFragment.Companion.Type.BESTSELLERS))
            }
            R.id.basket -> {
                BasketFragment.newInstance()
            }
            else -> CategoriesFragment.newInstance()
        }
    }

    private fun loadHomeFragment(navItemIndex: Int) : Boolean {
        applyFragmentTransaction(getFragment(navItemIndex))
        this.mainDrawer.closeDrawer(this.nav_view)
        return true
    }

    private fun applyFragmentTransaction(fragment: Fragment) {
        doAsync {
            supportFragmentManager.beginTransaction().apply {
                addToBackStack(null)
                setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                replace(R.id.frame, fragment)
            }.commitAllowingStateLoss()
        }
    }

    fun getActiveFragment() : Fragment {
        return supportFragmentManager.findFragmentById(R.id.frame)
    }

    fun validateBasketButton() {
        this.toolbarBasketPrice.text = mBasketService.calculateBasket().toString()
    }

    override fun onBackPressed() {
        if (this.mainDrawer.isDrawerOpen(this.nav_view)) {
            this.mainDrawer.closeDrawer(this.nav_view)
        }
        super.onBackPressed()

    }

    private fun loadData(database: AppDatabase) {
        doAsync {
            database.categoryDao().insert(
                    CategoryModel("Unknown"),
                    CategoryModel("Category1"),
                    CategoryModel("Category2"),
                    CategoryModel("Category3"),
                    CategoryModel("Category4")
            )

            database.bookDao().insert(
                    BookModel("00", "Karol", "Karol Wojtyla", 12.0, 3.3, "Bla bla bla",
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("01", "Title", "Karol Wojtyla", 12.0, 3.3, "Bla bla bla",
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("02", "Title", "Evve Evve", 11.22,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("03", "Title", category = "Category1", isBestseller = true,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("04", "Title", category = "Category1", isBestseller = true,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("05", "Title", category = "Category1", isBestseller = true,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("06", "Title", category = "Category2", isBestseller = true,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("07", "Title", category = "Category2", isBestseller = true,
                            photoUrl = "http://get.zlotemysli.pl/products/image/000/006/604/600x848.jpg"),
                    BookModel("08", "Title", category = "Category2", isBestseller = true),
                    BookModel("09", "Title", category = "Category3"),
                    BookModel("10", "Title", category = "Category3"),
                    BookModel("11", "Title", category = "Category4"),
                    BookModel("12", "Title", category = "Category4")
            )
        }
    }
}



