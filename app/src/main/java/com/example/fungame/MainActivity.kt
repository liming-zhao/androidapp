package com.example.fungame

// ...


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fungame.ui.theme.FunGameTheme
import kotlin.random.Random


@Composable
fun OnboardingScreen(
    onContinueClicked: () ->Unit,
    modifier: Modifier = Modifier) {
    // TODO: This state should be hoisted
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Column(
        //modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    FunGameTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FunGameTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

fun generateRandomIntegers(n: Int, rangeStart: Int = 2, rangeEnd: Int = 14): List<Int> {
    return List(n) { Random.nextInt(rangeStart, rangeEnd + 1) }
}

fun intToString(n: Int): String {
    val stringArray = arrayOf("J","Q","K","A")
    if(n > 0 && n <=10) {
        return "$n"
    }
    else return stringArray[n-11];
}


@Composable
fun MyApp(modifier: Modifier = Modifier){

    var mappingFun: (Int) -> String = {num -> intToString(num) }
    var shouldShowOnboarding by remember {mutableStateOf(true)}
      Surface(modifier)
      {
          Column(modifier = Modifier.padding((24.dp))) {

              OnboardingScreen(onContinueClicked = { shouldShowOnboarding = !shouldShowOnboarding})
              if(shouldShowOnboarding) {

                  val mylist: List<String> = generateRandomIntegers(4).map(mappingFun)

                  Greetings(names=mylist)
              }
          }
      }
    }

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Card(name = name)
        }
    }
}


@Composable
fun Card(name: String, modifier: Modifier = Modifier) {
    val expanded = remember {mutableStateOf(false)}
    val extrapadding = if (expanded.value) 48.dp else 0.dp
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical=4.dp,horizontal=8.dp)) {
        Row(modifier = Modifier.padding((24.dp))) {
            Column(modifier = modifier
                .weight(1f)
                .padding(bottom = extrapadding))
            {
                Text(
                    text = "Hello ",
                )
                Text(text = name)
            }
            ElevatedButton(onClick = {expanded.value = !expanded.value})
            {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}



@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    FunGameTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    FunGameTheme {
        MyApp(Modifier.fillMaxSize())
    }
}