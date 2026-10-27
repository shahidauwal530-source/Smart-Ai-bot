# ForexBot Pro — SaaS Multi-Broker Forex Auto Trading Engine

A high-performance algorithmic Forex trading and AI computer vision platform built with **Node.js + Express + MetaApi.cloud SDK + Google Gemini Vision API + Tailwind CSS + Paystack**.

---

## ⚡ Key Highlights & Architecture

- **Multi-Broker MT5 Execution:** Real-time bridge to **Exness (Exness-MT5Real)**, **JustMarkets (JustMarkets-Live)**, and **HFM (HFMarketsSV-Live)** via MetaApi.cloud RPC.
- **Gemini Vision AI Analysis:** Upload chart screenshots from TradingView or MetaTrader 5. Gemini 1.5 Flash reads candlestick structures, support/resistance, RSI divergence, and generates Entry, Stop Loss, and Take Profit with Hausa explanations (`reason_hausa`).
- **1-Click Multi-Trade Order Scaler:** Fires 1, 2, 3, 5, or 10 simultaneous orders with 400ms inter-trade delay and precision fast TP options.
- **7 Quantitative Automated Strategies:**
  1. 🔥 **London Breakout:** 08:00 - 11:00 UTC Asian high/low range breakout.
  2. 👑 **Gold Scalper (XAUUSD King):** M5 Bollinger Bands (20,2) + RSI 14 scalping.
  3. 🛡️ **Trend Rider (Safe):** EMA 9 > 21 > 50 alignment + RSI corridor.
  4. 🔄 **Mean Reversion:** 2+ ATR extension from 50 SMA pullback.
  5. 📰 **News Hunter:** High impact momentum during 12:00-14:00 & 18:00-20:00 UTC.
  6. ⚖️ **Safe Hedge:** Delta-neutral dual BUY & SELL locking with dynamic closure.
  7. 🌙 **Naija Overnight:** West Africa Time (21:00-05:00 WAT) low-spread channel trading.
- **Strict Risk Management:** Martingale is strictly OFF (locked safe mode), maximum 2% risk per trade, Stop Loss on every single position.
- **Paystack Payment Integration:** Seamless checkout for 3-Day Free Trial ($0), Starter ($15/mo), and Pro ($30/mo).
- **Telegram Live Alerts:** Instant push alerts for connections, signals, executions, and subscriptions.

---

## 🔐 Master Administrator Access (Requirement 1 & 2)

- **Default State:** Regular users **never see** the Admin Portal button or links.
- **Admin Visibility:**
  - URL parameter: `?admin=abba2025`
  - Browser Storage: `localStorage.getItem('isAdmin') === 'true'`
  - Keyboard Shortcut: Press <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>A</kbd> and enter password `abba2025`.
- **Backend Protection:** `GET /admin` returns `403 Forbidden` unless query `?admin=abba2025` or header `x-admin: abba2025` is present.
- **Permanent Free Pass:** User email `shahidauwal530@gmail.com` bypasses all trials, paywalls, and restrictions forever. Admin can connect up to 100 MT5 accounts simultaneously.

---

## 🚀 Quick Start Guide

### 1. Clone & Install Dependencies
```bash
npm install
```

### 2. Configure Environment (`.env`)
Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```

Edit your `.env` with your API credentials:
```env
PORT=3000

# 1. MetaApi.cloud Token
# Get free account at https://app.metaapi.cloud -> API Access -> Copy Token
METAAPI_TOKEN=your_metaapi_token_here

# 2. Google Gemini Vision API Key (Free)
# Get free key at https://aistudio.google.com -> Get API Key
GEMINI_KEY=your_gemini_api_key_here

# 3. Paystack Keys
# Get keys at https://dashboard.paystack.com/#/settings/developer
PAYSTACK_PUBLIC=pk_test_xxxxxxxxxxxxxxxxxxxxx
PAYSTACK_SECRET=sk_test_xxxxxxxxxxxxxxxxxxxxx

# 4. Telegram Alerts (Optional)
# Create a bot via @BotFather on Telegram, get your Chat ID via @userinfobot
TELEGRAM_BOT_TOKEN=
TELEGRAM_CHAT_ID=

# 5. Master Admin
ADMIN_EMAIL=shahidauwal530@gmail.com
ADMIN_PASS=abba2025
```

### 3. Launch Server
```bash
npm start
```
Then visit:
- **Landing Page:** [http://localhost:3000/index.html](http://localhost:3000/index.html)
- **Live Terminal:** [http://localhost:3000/dashboard.html](http://localhost:3000/dashboard.html)
- **Admin Portal:** [http://localhost:3000/admin?admin=abba2025](http://localhost:3000/admin?admin=abba2025)

---

## 📱 How to Get Free API Keys

1. **MetaApi.cloud (Free MT5 Cloud Hosting):**
   - Register at [metaapi.cloud](https://app.metaapi.cloud).
   - Under **API Access**, generate an API token and paste it into `METAAPI_TOKEN`.
   - *Note:* If no token is provided, the platform automatically runs in high-fidelity **MT5 Cloud Sandbox Simulation Mode** so you can test all features and strategies immediately.

2. **Google Gemini Vision API (100% Free):**
   - Go to [Google AI Studio](https://aistudio.google.com).
   - Click **Get API key** and generate a key for Gemini 1.5 Flash.
   - Paste into `GEMINI_KEY`.

3. **Paystack:**
   - Sign up at [paystack.com](https://paystack.com).
   - Copy your **Test Public Key** and **Test Secret Key** into `PAYSTACK_PUBLIC` and `PAYSTACK_SECRET`.

---

## 📊 Endpoints Summary

| Method | Route | Description |
|---|---|---|
| `GET` | `/admin` | Master admin portal (Requires `?admin=abba2025`) |
| `GET` | `/api/admin/overview` | Admin dashboard statistics (Protected by `isAdmin` middleware) |
| `GET` | `/api/strategies` | Returns JSON of all 7 trading strategies |
| `POST` | `/api/connect` | Connects MT5 account via MetaApi or Sandbox |
| `POST` | `/api/switch-account` | Clears current MT5 connection |
| `POST` | `/api/analyze-screenshot` | Gemini Vision candlestick AI pattern extraction |
| `POST` | `/api/open-multi-trade` | Scale-in batch order executor (1-10 trades) |
| `POST` | `/api/start` | Starts autonomous 30s loop with News Filter |
| `POST` | `/api/stop` | Stops autonomous trading loop |
| `GET` | `/api/status/:login` | Live balance, equity, PnL, open trades (5s polling) |
| `POST` | `/api/verify-payment` | Verifies Paystack reference & updates license |

---
&copy; 2026 ForexBot Pro. All rights reserved.
