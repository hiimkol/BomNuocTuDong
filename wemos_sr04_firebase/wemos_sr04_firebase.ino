#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>
#define trig D1     // chân trig của HC-SR04
#define echo D2     // chân echo của HC-SR04
#define relay D5

#define FIREBASE_HOST "ltn-bomnuoctudong.firebaseio.com" 
#define FIREBASE_AUTH ""   //Không dùng xác thực nên giữ nguyên
#define WIFI_SSID "2A20"  
#define WIFI_PASSWORD "quang123"



void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(trig, OUTPUT);  // chân trig phát tín hiệu
  pinMode(echo, INPUT);   // chân echo nhận tín hiệu
  pinMode(relay, OUTPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

}

void loop() {
  // put your main code here, to run repeatedly:
  unsigned long duration; // biến đo thời gian
  int distance;           // biến lưu khoảng cách
  int min, max;
  /* Phát xung từ chân trig */
  digitalWrite(trig, 0);  // tắt chân trig
  delayMicroseconds(2);
  digitalWrite(trig, 1);  // phát xung từ chân trig
  delayMicroseconds(5);   // xung có độ dài 5 microSeconds
  digitalWrite(trig, 0);  // tắt chân trig

  /* Tính toán thời gian */
  // Đo độ rộng xung HIGH ở chân echo.
  duration = pulseIn(echo, HIGH);
  // Tính khoảng cách đến vật.
  distance = int(duration / 2 / 29.412);
  Firebase.setFloat("current", distance);
  min = Firebase.getFloat("min");
  max = Firebase.getFloat("max");
  if (distance <= 10-max) {
    digitalWrite(relay, LOW);
  } else if (distance >= 10-min) {
    digitalWrite(relay, HIGH);
  }
  //digitalWrite(relay, distance >10);
  // Serial.println(digitalRead(relay)==1?"Bat":"Tat");
  Serial.println(distance);
  delay(200);
}

