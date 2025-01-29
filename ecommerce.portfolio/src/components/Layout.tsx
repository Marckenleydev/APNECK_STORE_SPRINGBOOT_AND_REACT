
import Header from './Header'
import { Outlet } from 'react-router'
import Footer from './Footer'

function Layout() {
  return (
    <div>
        <Header/>
        <Outlet/>
        <Footer/>
    </div>
  )
}

export default Layout