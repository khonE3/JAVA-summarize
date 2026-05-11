# ☕ JAVA-Summarize
https://khone3.github.io/JAVA-summarize/
> สรุป Concept คำสั่ง Java พร้อมตัวอย่างโค้ดและวิธีการนำไปใช้งาน

คำสั่งเล่นเกม java DisplayGame.java 
```
javac DisplayGame.java
java DisplayGame
```

## 📋 เกี่ยวกับโปรเจกต์

เว็บไซต์สรุปคำสั่งพื้นฐานของภาษา Java ออกแบบด้วย Glassmorphism UI, Dark Mode รองรับ Responsive ทุกขนาดหน้าจอ

### ✨ ฟีเจอร์
- 🎨 **Glassmorphism Dark UI** — ดีไซน์สวยงามทันสมัย
- 📱 **Responsive** — รองรับมือถือ, แท็บเล็ต, เดสก์ท็อป
- 🔍 **ค้นหาคำสั่ง** — กรองหัวข้อแบบ Real-time
- 📋 **Copy Code** — คัดลอกโค้ดได้ในคลิกเดียว
- 🎯 **Scroll Spy** — Sidebar ไฮไลต์ตามตำแหน่งที่อ่าน
- 🌈 **Syntax Highlighting** — แสดงสีโค้ดสวยงาม

## 📚 หัวข้อที่ครอบคลุม

| # | หัวข้อ | คำอธิบาย |
|---|--------|----------|
| 1 | **Variables** | การประกาศตัวแปร char, String, int, float, double, final |
| 2 | **Print Output** | print, println, printf และ format specifiers |
| 3 | **Type Casting** | Widening (อัตโนมัติ) และ Narrowing (manual) |
| 4 | **String Methods** | length, toUpperCase, toLowerCase, indexOf, concatenation |
| 5 | **Math Class** | random, max, min, sqrt, abs |
| 6 | **Array** | การประกาศ, เข้าถึง, วนลูป Array |
| 7 | **Methods** | void, return, Overloading |
| 8 | **Scanner** | nextLine, nextInt, nextDouble — รับ input จากผู้ใช้ |

## 🛠️ เทคโนโลยี

- **HTML5** — โครงสร้างเว็บ
- **CSS3** — Glassmorphism, Animations, Responsive
- **JavaScript** — Search, Copy, Scroll Spy, Sidebar Toggle

## 🚀 วิธีใช้งาน

```bash
# Clone โปรเจกต์
git clone https://github.com/your-username/JAVA-summarize.git

# เปิดไฟล์ index.html ในเบราว์เซอร์
start index.html
```

## 📁 โครงสร้างไฟล์

```
JAVA-summarize/
├── index.html         # หน้าเว็บหลัก
├── style.css          # สไตล์ทั้งหมด
├── script.js          # JavaScript ฟังก์ชัน
├── ConceptJAVA.java   # ซอร์สโค้ด Java ต้นฉบับ
└── README.md          # ไฟล์นี้
```

## 📖 สรุปคำสั่ง Java

### 1. การประกาศตัวแปร
```java
char c = 'J';
String s = "Hello";
int i = 3;
float f = 1.66f;      // ต้องมี f
double d = 3.3456;
final int CONST = 15;  // ค่าคงที่
```

### 2. การแสดงผล
```java
System.out.print("No newline");
System.out.println("With newline");
System.out.printf("Format: %d %f %s", 10, 3.14, "Hi");
```

### 3. Type Casting
```java
// Widening (อัตโนมัติ): byte → short → int → long → float → double
int a = 10;
double b = a;  // 10.0

// Narrowing (manual): double → float → long → int
double x = 9.21;
long y = (long) x;  // 9
```

### 4. String Methods
```java
String s = "Hello";
s.length();       // 5
s.toUpperCase();  // HELLO
s.toLowerCase();  // hello
s.indexOf("l");   // 2
"12" + 20;        // "1220"
```

### 5. Math
```java
int r = (int)(Math.random() * 101); // 0-100
Math.max(5, 10);  // 10
Math.sqrt(64);    // 8.0
```

### 6. Array
```java
String[] cars = {"Volvo", "BMW", "Ford"};
System.out.println(cars[0]); // Volvo
for (String c : cars) { System.out.println(c); }
```

### 7. Methods
```java
// void method
static void greet(String name) {
    System.out.println("Hi " + name);
}

// return method
static int add(int a, int b) { return a + b; }

// overloading
static int plus(int a, int b) { return a + b; }
static double plus(double a, double b) { return a + b; }
```

### 8. Scanner
```java
import java.util.Scanner;
Scanner sc = new Scanner(System.in);
String name = sc.nextLine();
int age = sc.nextInt();
```

## 📄 License

MIT License — ใช้เพื่อการเรียนรู้ได้อย่างอิสระ
