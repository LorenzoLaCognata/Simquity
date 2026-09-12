import { useEffect, useState } from 'react'
import { ComposableMap, Geographies, Geography, Marker } from 'react-simple-maps'
import { API_URL } from './config.js'

const GEO_URL = 'https://cdn.jsdelivr.net/npm/world-atlas@2/countries-110m.json'

function WorldMap() {
  const [regions, setRegions] = useState([])

  useEffect(() => {
    fetch(`${API_URL}/api/regions`)
      .then((response) => response.json())
      .then(setRegions)
  }, [])

  const plottable = regions.filter((r) => r.latitude != null && r.longitude != null)

  return (
    <div>
      <ComposableMap projectionConfig={{ scale: 140 }} style={{ width: '100%', height: 'auto' }}>
        <Geographies geography={GEO_URL}>
          {({ geographies }) =>
            geographies.map((geo) => (
              <Geography
                key={geo.rsmKey}
                geography={geo}
                fill="#e3e1da"
                stroke="#ffffff"
                strokeWidth={0.5}
              />
            ))
          }
        </Geographies>
        {plottable.map((region) => (
          <Marker key={region.id} coordinates={[region.longitude, region.latitude]}>
            <circle r={4} fill="#2a5db0" stroke="#ffffff" strokeWidth={1} />
          </Marker>
        ))}
      </ComposableMap>
      {plottable.length === 0 && (
        <p className="empty-note">
          No regions with coordinates yet - markers will appear here once the simulation creates some.
        </p>
      )}
    </div>
  )
}

export default WorldMap
