import { useEffect, useState } from 'react'
import { API_URL } from './config.js'
import {
  MapPin, Users, Building2, Heart, Briefcase, Hammer, GraduationCap,
  Package, Coins, ArrowLeftRight, Store, Wallet, Zap, Activity as ActivityIcon,
  CalendarClock, Cpu, Home, School as SchoolIcon, Users2, Landmark,
  ScrollText, Factory as FactoryIcon, Route as RouteIcon, Languages,
} from 'lucide-react'

const ICONS = {
  regions: MapPin,
  agents: Users,
  organizations: Building2,
  relationships: Heart,
  sectors: Briefcase,
  professions: Hammer,
  skills: GraduationCap,
  agentSkills: GraduationCap,
  assets: Package,
  currencies: Coins,
  transactions: ArrowLeftRight,
  markets: Store,
  employments: Wallet,
  activityTypes: Zap,
  activities: ActivityIcon,
  eventTypes: CalendarClock,
  events: CalendarClock,
  technologies: Cpu,
  households: Home,
  schools: SchoolIcon,
  guilds: Users2,
  governments: Landmark,
  policies: ScrollText,
  factories: FactoryIcon,
  routes: RouteIcon,
  languages: Languages,
}

function formatLabel(key) {
  const withSpaces = key.replace(/([A-Z])/g, ' $1')
  return withSpaces.charAt(0).toUpperCase() + withSpaces.slice(1)
}

function WorldSummary() {
  const [counts, setCounts] = useState(null)

  useEffect(() => {
    fetch(`${API_URL}/api/world/summary`)
      .then((response) => response.json())
      .then(setCounts)
  }, [])

  if (!counts) {
    return <p className="empty-note">Loading world summary...</p>
  }

  return (
    <div className="summary-grid">
      {Object.entries(counts).map(([key, value]) => {
        const Icon = ICONS[key] || Package
        return (
          <div className="summary-card" key={key}>
            <Icon size={18} className="summary-icon" />
            <div className="count">{value}</div>
            <div className="label">{formatLabel(key)}</div>
          </div>
        )
      })}
    </div>
  )
}

export default WorldSummary