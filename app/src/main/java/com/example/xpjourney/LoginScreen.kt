package com.example.xpjourney
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xpjourney.ui.theme.XPJourneyTheme

// Define the routes for navigation
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
}

// Custom Colors based on the image
val PrimaryBlue = Color(0xFFA0B8F5)
val ButtonPink = Color(0xFFFF69B4) // Hot Pink
val IconCircleColor = Color(0xFFE6E6FA) // Light Lavender/Periwinkle for the circle

/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPJourneyTheme {
                AppNavigation()
            }
        }
    }
}*/

/*@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.SignUp.route) },
                onNavigateToForgot = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(Screen.SignUp.route) {
            DetailedSignUpScreen(
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}*/

// --- LOGIN SCREEN (FIXED TEXT INPUT) ---
@Composable
fun LoginScreen(/*onNavigateToRegister: () -> Unit, onNavigateToForgot: () -> Unit, onLoginSuccess: () -> Unit*/ navController: NavController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // ICON BLOCK
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(IconCircleColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Person, // Placeholder Icon
                    contentDescription = "XPJourney Icon",
                    tint = PrimaryBlue,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "XPJOURNEY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }
        // END ICON BLOCK

        Spacer(modifier = Modifier.height(40.dp))

        // Title: XPJourney
        Text(
            text = "XPJourney",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(3f, 3f),
                    blurRadius = 5f
                )
            )
        )

        Spacer(modifier = Modifier.height(40.dp)) // Space before inputs

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                //.height(50.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                //.height(50.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Login and Forgot Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                        navController.navigate("dashboard")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(end = 8.dp)
            ) {
                Text("Login", color = Color.White)
            }
            Button(
                onClick = {navController.navigate("forgot") },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(start = 8.dp)
            ) {
                Text("Forgot?", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign in with Google Button
        Button(
            onClick = { /* Handle Google Sign-in */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Sign in With Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Divider Line
        HorizontalDivider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Register Button
        Button(
            onClick = {navController.navigate("register")},
            colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Register", color = Color.White)
        }
    }
}

// --- FORGOT PASSWORD SCREEN ---
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Icon and Text Area (Same as other screens)
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(IconCircleColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Lock, // Changed icon to Lock for password reset
                    contentDescription = "Password Reset Icon",
                    tint = PrimaryBlue,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "RESET PASSWORD",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Reset Password",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        // Input Fields
        SignUpInputField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "New Password",
            icon = Icons.Default.Lock,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpInputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            icon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Reset Button
        Button(
            onClick = {
                // Handle password reset logic
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
        ) {
            Text("Reset Password", color = Color.White)
        }
    }
}

// --- DETAILED SIGN UP SCREEN ---
@Composable
fun DetailedSignUpScreen(navController: NavController) {
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Icon and Text Area
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(IconCircleColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Person, // Placeholder Icon
                    contentDescription = "XPJourney Icon",
                    tint = PrimaryBlue,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "XPJOURNEY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Input Fields
        SignUpInputField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First name",
            icon = Icons.Default.Person
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpInputField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Last name",
            icon = Icons.Default.Person
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpInputField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            icon = Icons.Default.MailOutline
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpInputField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            icon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Login Button (Matching the image)
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
        ) {
            Text("Login", color = Color.White) // Keeping "Login" to match the image
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sign in with Google Button
        Button(
            onClick = { /* Handle Google Sign-in */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .width(200.dp)
                .height(30.dp)
        ) {
            Text("Sign in With Google G", color = Color.Black, fontSize = 12.sp)
        }
    }
}

// Helper Composable for the Input Fields
@Composable
fun SignUpInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
    )
}

// --- HOME SCREEN ---
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to XPJourney! You are logged in.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

/*@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    XPJourneyTheme {
        ForgotPasswordScreen(onNavigateToLogin = {})
    }
}*/
