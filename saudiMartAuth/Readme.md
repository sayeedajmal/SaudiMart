# REDIS DESIGN — `saudiMartAuth` (SaudiMart)

| Purpose                | Redis Key                 | Value Example                             | TTL           |
| ---------------------- | ------------------------- | ----------------------------------------- | ------------- |
| **OTP**                | `otp:sayeed@gmail.com`    | `"492173"`                                | 300s (5 min)  |
| **Rate Limit – Email** | `rate:sayeed@gmail.com`   | `3` (failed login attempts)               | 900s (15 min) |
| **Rate Limit – IP**    | `rate:192.168.0.5`        | `15` (requests from IP)                   | 60s (1 min)   |
| **User Cache**         | `user:sayeed@gmail.com`   | JSON: `{ email, name, orgId, role, ... }` | 300–1800 sec  |
| **Blacklisted Token**  | `blacklist:jti-abc123xyz` | `"true"`                                  | JWT expiry    |

---

### OTP

**Key:** `otp:sayeed@gmail.com`
**Value:**

```json
"492173"
```

---

### Rate Limit by Email

**Key:** `rate:sayeed@gmail.com`
**Value:**

```json
3
```

---

### Rate Limit by IP

**Key:** `rate:192.168.0.5`
**Value:**

```json
15
```

---

### Cached User

**Key:** `user:sayeed@gmail.com`
**Value:**

```json
{
  "userId": 102,
  "email": "sayeed@gmail.com",
  "name": "Sayeed Ajmal",
  "role": "BUYER_ADMIN",
  "organizationId": 2002,
  "department": "Procurement",
  "position": "Manager",
  "ip": "192.168.0.5"
}
```
