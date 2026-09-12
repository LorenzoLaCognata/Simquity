import { useEffect, useRef, useState } from 'react'
import Chart from 'chart.js/auto'
import { API_URL } from './config.js'

function HistoryChart({ day }) {
  const canvasRef = useRef(null)
  const chartInstance = useRef(null)
  const [history, setHistory] = useState([])

  useEffect(() => {
    fetch(`${API_URL}/api/simulation/history`)
      .then((response) => response.json())
      .then(setHistory)
  }, [day])

  useEffect(() => {
    if (!canvasRef.current) return

    if (chartInstance.current) {
      chartInstance.current.destroy()
    }

    chartInstance.current = new Chart(canvasRef.current, {
      type: 'line',
      data: {
        labels: history.map((point) =>
          new Date(point.recordedAt).toLocaleTimeString()
        ),
        datasets: [
          {
            data: history.map((point) => point.day),
            borderColor: '#2a5db0',
            backgroundColor: 'rgba(42, 93, 176, 0.08)',
            fill: true,
            tension: 0.3,
            borderWidth: 2,
            pointRadius: 0,
            pointHoverRadius: 4,
            pointBackgroundColor: '#2a5db0',
          },
        ],
      },
      options: {
        responsive: true,
        animation: false,
        plugins: { legend: { display: false } },
        scales: {
          y: {
            beginAtZero: true,
            grid: { color: '#e3e1da' },
            ticks: { color: '#7a7870' },
          },
          x: {
            grid: { display: false },
            ticks: { color: '#7a7870', maxTicksLimit: 6 },
          },
        },
      },
    })
  }, [history])

  if (history.length === 0) {
    return <p className="empty-note">No history yet - click Play to start generating data.</p>
  }

  return <canvas ref={canvasRef} height="120"></canvas>
}

export default HistoryChart
