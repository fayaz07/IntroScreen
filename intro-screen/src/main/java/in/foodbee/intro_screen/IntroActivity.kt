package `in`.foodbee.intro_screen

import `in`.foodbee.intro_screen.databinding.ActivityIntroBinding
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import java.util.Timer
import java.util.TimerTask

open class IntroActivity : AppCompatActivity() {

  lateinit var binding: ActivityIntroBinding

  var titleArray: Array<String>? = null

  var subtitleText: String? = null

  var imageArray: Array<Drawable?>? = null

  var dotInactiveColor: Int? = null
  var dotActiveColor: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setView()
  }

  private fun setView() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    )

    supportActionBar?.hide()

    binding = ActivityIntroBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.subtitleTv.text = subtitleText ?: ""

    setUpViewPager()
  }

  private fun setUpViewPager() {
    addBottomDotsAndText(0)
    val handler = Handler()
    val update = Runnable {
      binding.sliderViewPager.currentItem = (binding.sliderViewPager.currentItem + 1) % 3
    }
    val timer = Timer()
    timer.schedule(object : TimerTask() {
      override fun run() {
        handler.post(update)
      }
    }, 2000, 2000)

    val myViewPagerAdapter = IntroViewPagerAdapter(imageArray, this)
    binding.sliderViewPager.adapter = myViewPagerAdapter
    val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
      override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
      ) {
      }

      override fun onPageSelected(position: Int) {
        addBottomDotsAndText(position)
      }

      override fun onPageScrollStateChanged(state: Int) {}
    }
    binding.sliderViewPager.addOnPageChangeListener(viewPagerPageChangeListener)
  }

  private fun addBottomDotsAndText(i: Int) {
    binding.headlineTv.text = titleArray?.get(i)
    when (i) {
      0 -> {
        binding.apply {
          dot0Tv.setTextColor(dotActiveColor ?: android.R.color.black)
          dot1Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
          dot2Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
        }
      }
      1 -> {
        binding.apply {
          dot0Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
          dot1Tv.setTextColor(dotActiveColor ?: android.R.color.black)
          dot2Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
        }
      }
      2 -> {
        binding.apply {
          dot0Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
          dot1Tv.setTextColor(dotInactiveColor ?: android.R.color.black)
          dot2Tv.setTextColor(dotActiveColor ?: android.R.color.black)
        }
      }
    }

  }
}