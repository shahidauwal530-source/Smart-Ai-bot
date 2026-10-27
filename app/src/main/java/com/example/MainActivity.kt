package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCard
import com.example.ui.theme.ForexBotTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ForexBotTheme {
        ForexBotApp()
      }
    }
  }
}

sealed class NavItem(val title: String, val icon: ImageVector, val url: String, val tag: String) {
  object Dashboard : NavItem("Terminal", Icons.Default.Timeline, "file:///android_asset/web/dashboard.html", "nav_terminal")
  object Landing : NavItem("Home / Plans", Icons.Default.Bolt, "file:///android_asset/web/index.html", "nav_home")
  object Guide : NavItem("Setup Guide", Icons.Default.Info, "file:///android_asset/web/index.html#setup-guide", "nav_guide")
  object Admin : NavItem("Admin", Icons.Default.Security, "file:///android_asset/web/dashboard.html?admin=abba2025", "nav_admin")
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ForexBotApp() {
  var selectedTab by remember { mutableStateOf<NavItem>(NavItem.Dashboard) }
  var webViewRef by remember { mutableStateOf<WebView?>(null) }
  var logoTapCount by remember { mutableIntStateOf(0) }
  var isAdminUnlocked by remember { mutableStateOf(false) }
  val context = LocalContext.current

  BackHandler(enabled = webViewRef?.canGoBack() == true) {
    webViewRef?.goBack()
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkBackground),
    topBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(DarkBackground)
          .padding(horizontal = 16.dp, vertical = 10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
              logoTapCount++
              if (logoTapCount >= 5 && !isAdminUnlocked) {
                isAdminUnlocked = true
                Toast.makeText(context, "Super Admin Mode Unlocked (abba2025)", Toast.LENGTH_LONG).show()
                selectedTab = NavItem.Admin
              }
            }
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .background(DarkCard, RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "⚡",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BrandGreen
              )
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "ForexBot",
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 17.sp,
                  color = TextPrimary
                )
                Text(
                  text = ".Pro",
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 17.sp,
                  color = BrandGreen
                )
              }
              Text(
                text = "Exness • JustMarkets • HFM",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
              )
            }
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            if (isAdminUnlocked) {
              Box(
                modifier = Modifier
                  .background(Color(0x33F59E0B), RoundedCornerShape(6.dp))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "ADMIN ACTIVE",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFFBBF24)
                )
              }
            }
          }
        }
      }
    },
    bottomBar = {
      NavigationBar(
        containerColor = DarkCard,
        tonalElevation = 8.dp,
        modifier = Modifier.height(64.dp)
      ) {
        val navItems = mutableListOf<NavItem>(
          NavItem.Dashboard,
          NavItem.Landing,
          NavItem.Guide
        )
        if (isAdminUnlocked) {
          navItems.add(NavItem.Admin)
        }

        navItems.forEach { item ->
          val isSelected = selectedTab == item
          NavigationBarItem(
            selected = isSelected,
            onClick = {
              selectedTab = item
              webViewRef?.loadUrl(item.url)
            },
            icon = {
              Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(20.dp)
              )
            },
            label = {
              Text(
                text = item.title,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Color.Black,
              selectedTextColor = BrandGreen,
              indicatorColor = BrandGreen,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag(item.tag)
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(DarkBackground)
    ) {
      AndroidView(
        factory = { ctx ->
          WebView(ctx).apply {
            layoutParams = ViewGroup.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.apply {
              javaScriptEnabled = true
              domStorageEnabled = true
              loadWithOverviewMode = true
              useWideViewPort = true
              allowFileAccess = true
              allowContentAccess = true
              mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
              cacheMode = WebSettings.LOAD_DEFAULT
            }
            webViewClient = object : WebViewClient() {
              override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
              ): Boolean {
                return false
              }
            }
            webChromeClient = WebChromeClient()
            setBackgroundColor(android.graphics.Color.parseColor("#0a0a0a"))
            loadUrl(selectedTab.url)
            webViewRef = this
          }
        },
        update = { webView ->
          if (webView.url != selectedTab.url) {
            webView.loadUrl(selectedTab.url)
          }
        },
        modifier = Modifier
          .fillMaxSize()
          .testTag("forexbot_webview")
      )
    }
  }
}
