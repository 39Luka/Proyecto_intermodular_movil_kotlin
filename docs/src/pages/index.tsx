import type {ReactNode} from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';
import Heading from '@theme/Heading';

import styles from './index.module.css';

function HomepageHeader() {
  const {siteConfig} = useDocusaurusContext();
  return (
    <header className={clsx('hero', styles.heroBanner)}>
      <div className="container">
        <img
          src={useBaseUrl('/img/logo.svg')}
          alt="La Croassantina Logo"
          style={{
            width: '120px',
            marginBottom: '1rem',
            filter: 'brightness(0) invert(1)' // Forzar logo a blanco en el hero
          }}
        />
        <Heading as="h1" className="hero__title" style={{color: 'white'}}>
          {siteConfig.title}
        </Heading>
        <p className="hero__subtitle" style={{color: 'white'}}>{siteConfig.tagline}</p>
        <div className={styles.buttons}>
          <Link
            className="button button--lg"
            style={{
              marginRight: '1rem',
              display: 'flex',
              alignItems: 'center',
              backgroundColor: 'white',
              color: '#7F618F'
            }}
            to="/docs/user/intro">
            📖 Manual de Usuario
          </Link>
          <Link
            className="button button--outline button--lg"
            style={{
              color: 'white',
              borderColor: 'white',
              display: 'flex',
              alignItems: 'center'
            }}
            to="/docs/developer/intro">
            🛠️ Manual de Desarrollador
          </Link>
        </div>
      </div>
    </header>
  );
}

export default function Home(): ReactNode {
  const {siteConfig} = useDocusaurusContext();
  return (
    <Layout
      title={`${siteConfig.title}`}
      description="Documentación oficial de La Croassantina — App Móvil y Web">
      <HomepageHeader />
      <main>
        <HomepageFeatures />
      </main>
    </Layout>
  );
}
