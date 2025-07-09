# 💼 Freelancer Hub: Full-Stack Freelance Platformu

Freelancer Hub, freelance çalışanlar ile işverenleri buluşturan modern ve güvenli bir iş platformudur. Kullanıcılar ilan oluşturabilir, başvurular alabilir, portföylerini sergileyebilir, yeteneklerini tanımlayabilir, gerçek zamanlı mesajlaşabilir ve belge paylaşabilir.

---

## 🌟 Öne Çıkan Özellikler

* 👥 Rol bazlı kullanıcı yönetimi (Freelancer, Employer, Admin)
* 📝 İş ilanı oluşturma ve başvuru sistemi
* 💬 Gerçek zamanlı mesajlaşma (WebSocket + STOMP)
* 📎 Dosya yükleme ve önizleme/indirme
* 📁 Portföy ve yetenek yönetimi
* 🧾 Admin paneli ile kategori, alt kategori ve hizmet türü tanımı
* 🐳 Docker desteğiyle kolay dağıtım

---

## ⚙️ Kullanılan Teknolojiler

| Katman           | Teknolojiler                                                                   |
| ---------------- | ------------------------------------------------------------------------------ |
| Backend          | Spring Boot, Spring Security (JWT), WebSocket (STOMP), JPA (Hibernate), Lombok |
| Frontend         | HTML, CSS, Vanilla JS, SockJS, STOMP.js                                        |
| Veritabanı       | PostgreSQL                                                                     |
| Güvenlik         | JWT Token + Role-based Authorization                                           |
| Dosya Sistemi    | Multipart Upload + UUID tabanlı local dosya yönetimi                           |
| Konteynerizasyon | Docker                                                                         |

---

## 🔐 Authentication & Authorization

**Endpoint:** `/api/auth`

| Yöntem | Yol         | Açıklama                                         |
| ------ | ----------- | ------------------------------------------------ |
| POST   | `/register` | Kullanıcı kaydı (freelancer/employer rol seçimi) |
| POST   | `/login`    | Giriş işlemi                                     |
| POST   | `/logout`   | Çıkış yapar                                      |
| GET    | `/me`       | Token ile kullanıcı bilgisi                      |
| DELETE | `/user`     | Hesap silme                                      |

Roller: `FREELANCER`, `EMPLOYER`, `ADMIN`

---

## 👤 Kullanıcı Profili & Portföy

**Endpoint:** `/rest/api/user`

* GET `/user` → Profil bilgisi
* PUT `/user` → Profil güncelleme
* PUT `/user/contact` → İletişim bilgisi güncelle
* GET `/user/list` → Kullanıcı listesi (sohbet başlatma)

**Yetenek Yönetimi:** `/rest/api/skills`

* POST `/add` → Yetenek ekle

**Portföy Yönetimi:** `/rest/api/portfolio`

* POST `/create` → Portföy oluştur
* GET `/{username}` → Kullanıcı portföyünü getir
* DELETE `/{id}` → Portföy sil

**Yorumlar:** `/rest/api/portfolios/{id}/comments`

* POST → Yorum ekle
* GET → Yorumları getir

---

## 📄 İş İlanları

**Endpoint:** `/rest/api/jobs`

* POST `/` → Yeni ilan oluştur (EMPLOYER)
* GET `/` → Filtreli ilan listesi
* PUT `/{id}` → İlan güncelle
* DELETE `/{id}` → İlan sil

**Admin Paneli (Yönetimsel Tanımlar):** `/rest/api/admin`

* POST `/categories`, `/subcategories`, `/service-types`, `/feature-definitions`

---

## 📝 Başvuru Sistemi

**Endpoint:** `/rest/api/applications`

* POST `/` → Başvuru gönder (FREELANCER)
* GET `/my-applications` → Freelancer başvuruları
* GET `/my-job-applications` → Employer'a gelen başvurular
* PUT `/{id}/reply` → Başvuruyu onayla / reddet

---

## 💬 Anlık Mesajlaşma Sistemi

**WebSocket:**

* Gönderim: `@MessageMapping("/chat.send")`
* Dinleme: `/topic/chat.{chatId}`

**Mesaj Türleri:**

* Metin mesajı
* Görsel önizleme (image/jpeg/png)
* Diğer dosyalar (pdf, docx, xlsx) → indirme linki ile

**REST Endpoint:**

* GET `/api/chats/id?user1={u1}&user2={u2}` → Ortak chat ID'sini getir

---

## 📎 Dosya Paylaşımı

**Endpoint:** `/api/files`

* POST `/upload` → Dosya yükleme (UUID + orijinal isim)
* GET `/download/{filename}` → Dosya indirme (authentication gerektirir)

```properties
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
```

---

## 🧪 Kurulum Adımları

```bash
git clone https://github.com/oguzhansandi/Freelancer-hub-project.git
cd Freelancer-hub-project

# Maven ile projeyi derle
mvn clean install

# Spring Boot uygulamasını başlat
mvn spring-boot:run
```

Web arayüzüne erişim: `http://localhost:8080/chat.html`

---

## 👨‍💻 Geliştirici Bilgileri

**Oğuzhan Sandı**
GitHub: [oguzhansandi](https://github.com/oguzhansandi)

Bu proje, full-stack geliştirme yetkinliklerini sergilemek, modern yazılım mimarisi prensiplerini uygulamak ve gerçek dünya problemi üzerine çözüm üretmek amacıyla geliştirilmiştir.
