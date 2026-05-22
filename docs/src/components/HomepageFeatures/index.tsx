import type {ReactNode} from 'react';
import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  emoji: string;
  description: ReactNode;
};

const FeatureList: FeatureItem[] = [
  {
    title: 'Experiencia Nativa Fluida',
    emoji: '📱',
    description: (
      <>
        Interfaz construida 100% con Jetpack Compose, garantizando una navegación fluida,
        animaciones nativas y un rendimiento optimizado para dispositivos Android.
      </>
    ),
  },
  {
    title: 'Gestión de Pedidos Táctil',
    emoji: '🥐',
    description: (
      <>
        Proceso de compra intuitivo: catálogo por categorías, carrito reactivo con cálculo de totales
        en tiempo real y aplicación de promociones exclusivas.
      </>
    ),
  },
  {
    title: 'Seguridad y Persistencia',
    emoji: '🔐',
    description: (
      <>
        Autenticación robusta con JWT, almacenamiento seguro de credenciales mediante Jetpack DataStore
        y sesión persistente para una experiencia sin interrupciones.
      </>
    ),
  },
];

function Feature({title, emoji, description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className={clsx('card', styles.featureCard)}>
        <div className="card__header text--center">
          <div style={{fontSize: '3rem', marginBottom: '1rem'}}>{emoji}</div>
          <Heading as="h3">{title}</Heading>
        </div>
        <div className="card__body text--center">
          <p>{description}</p>
        </div>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): ReactNode {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
