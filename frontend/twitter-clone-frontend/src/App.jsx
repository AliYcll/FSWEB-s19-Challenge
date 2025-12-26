import { useState } from 'react'
import './App.css'

function App() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [token, setToken] = useState('')
  const [userId, setUserId] = useState('1')
  const [tweets, setTweets] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [message, setMessage] = useState('')

  const handleLogin = async () => {
    setError('')
    setMessage('')
    setLoading(true)
    try {
      const response = await fetch('http://localhost:3000/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      })

      if (!response.ok) {
        throw new Error('Login failed')
      }

      const data = await response.json()
      setToken(data.token || '')
      setMessage('Signed in. Token ready.')
    } catch (err) {
      setError(err.message || 'Login failed')
    } finally {
      setLoading(false)
    }
  }

  const handleFetchTweets = async () => {
    setError('')
    setMessage('')
    setLoading(true)
    try {
      const response = await fetch(
        `http://localhost:3000/tweet/findByUserId?userId=${encodeURIComponent(userId)}`,
        {
          headers: token ? { Authorization: `Bearer ${token}` } : {}
        }
      )

      if (!response.ok) {
        throw new Error('Failed to load tweets')
      }

      const data = await response.json()
      setTweets(Array.isArray(data) ? data : [])
    } catch (err) {
      setError(err.message || 'Failed to load tweets')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="app">
      <header className="app-header">
        <div>
          <p className="eyebrow">FSWEB Challenge</p>
          <h1>Tweet Pulse</h1>
          <p className="subtitle">
            Login, pick a user, and pull tweets from your Spring Boot API.
          </p>
        </div>
        <div className="status-chip">
          {token ? 'Token ready' : 'No token yet'}
        </div>
      </header>

      <main className="layout">
        <section className="panel auth">
          <h2>Sign In</h2>
          <p>Use your registered email to get a JWT token.</p>

          <label>
            Email
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="you@example.com"
            />
          </label>
          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="your password"
            />
          </label>
          <button onClick={handleLogin} disabled={loading}>
            {loading ? 'Signing in...' : 'Sign in'}
          </button>
        </section>

        <section className="panel feed">
          <div className="feed-header">
            <h2>Tweet Feed</h2>
            <div className="controls">
              <label>
                User ID
                <input
                  type="number"
                  min="1"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                />
              </label>
              <button onClick={handleFetchTweets} disabled={loading}>
                {loading ? 'Loading...' : 'Fetch tweets'}
              </button>
            </div>
          </div>

          {error ? <div className="alert error">{error}</div> : null}
          {message ? <div className="alert success">{message}</div> : null}

          <div className="tweet-list">
            {tweets.length === 0 ? (
              <div className="empty-state">
                No tweets yet. Try another user ID.
              </div>
            ) : (
              tweets.map((tweet) => (
                <article className="tweet-card" key={tweet.id}>
                  <div className="tweet-meta">
                    <span>Tweet #{tweet.id}</span>
                    <span>User {tweet.user?.id ?? userId}</span>
                  </div>
                  <p>{tweet.content}</p>
                </article>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  )
}

export default App
