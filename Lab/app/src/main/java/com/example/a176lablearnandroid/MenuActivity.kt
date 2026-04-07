// checking 24/2/2026

package com.example.a176lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat


class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val view = LocalView.current

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeCustomAnimation(
                                context, android.R.anim.fade_in, android.R.anim.fade_out
                            )
                            startActivity(Intent(this@MenuActivity, RPGCardActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "RPGCardActivity (Fade In/Out)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeCustomAnimation(
                                context, android.R.anim.slide_in_left, android.R.anim.slide_out_right
                            )
                            startActivity(Intent(this@MenuActivity, PokedexActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "PokedexActivity (Slide In Left)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                                view, 0, 0, view.width, view.height
                            )
                            startActivity(Intent(this@MenuActivity, LifeCycleComposeActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "LifeCycleComposeActivity (Scale Up)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeClipRevealAnimation(
                                view, view.width / 2, view.height / 2, view.width, view.height
                            )
                            startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "SharedPreferencesActivity (Clip Reveal)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MenuActivity)
                            startActivity(Intent(this@MenuActivity, GalleryActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "GalleryActivity (Scene Transition)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeTaskLaunchBehind()
                            startActivity(Intent(this@MenuActivity, SensorActivity::class.java), options.toBundle())
                        }) {
                            Text(text = "SensorActivity (Task Launch Behind)")
                        }
                    }

                    item {
                        Button(onClick = {
                            startActivity(Intent(this@MenuActivity, Part1AnimationActivity::class.java))
                            @Suppress("DEPRECATION")
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out)
                        }) {
                            Text(text = "Part1AnimationActivity (Legacy Override Slide)")
                        }
                    }

                    item {
                        Button(onClick = {
                            startActivity(Intent(this@MenuActivity, Part2Activity::class.java))
                            @Suppress("DEPRECATION")
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }) {
                            Text(text = "Part2Activity (Legacy Override Fade)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeBasic()
                            startActivity(Intent(this@MenuActivity, Part3Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part3Activity (Basic Default)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeCustomAnimation(
                                context, android.R.anim.fade_in, android.R.anim.slide_out_right
                            )
                            startActivity(Intent(this@MenuActivity, Part4Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part4Activity (Fade In / Slide Out)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                                view, view.width / 2, view.height / 2, 0, 0
                            )
                            startActivity(Intent(this@MenuActivity, Part5Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part5Activity (Scale Up Center)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeClipRevealAnimation(
                                view, 0, 0, view.width, view.height
                            )
                            startActivity(Intent(this@MenuActivity, Part6Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part6Activity (Clip Reveal Top-Left)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MenuActivity)
                            startActivity(Intent(this@MenuActivity, Part8Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part8Activity (Scene Transition Auto)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeBasic()
                            startActivity(Intent(this@MenuActivity, Part9Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part9Activity (Collapsing Toolbar)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeBasic()
                            startActivity(Intent(this@MenuActivity, Part10Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part10Activity (Glance App Widget)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeBasic()
                            startActivity(Intent(this@MenuActivity, Part11Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part11Activity (Skeleton Loading)")
                        }
                    }

                    item {
                        Button(onClick = {
                            val options = ActivityOptionsCompat.makeBasic()
                            startActivity(Intent(this@MenuActivity, Part12Activity::class.java), options.toBundle())
                        }) {
                            Text(text = "Part12Activity (Modal Bottom Sheet & Dialog)")
                        }
                    }
                }
            }
        }
    }
}
