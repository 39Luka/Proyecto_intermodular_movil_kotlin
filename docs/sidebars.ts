import type {SidebarsConfig} from '@docusaurus/plugin-content-docs';

const sidebars: SidebarsConfig = {
  userSidebar: [
    {
      type: 'category',
      label: 'Guía de Usuario',
      items: [
        'user/intro',
        'user/mobile-intro',
        {
          type: 'category',
          label: 'App Móvil',
          items: [
            'user/mobile/auth',
            'user/mobile/compra',
          ],
        },
        'user/getting-started',
        'user/features',
      ],
    },
  ],

  devSidebar: [
    {
      type: 'category',
      label: 'Guía de Desarrollador',
      items: [
        'developer/intro',
        {
          type: 'category',
          label: 'App Móvil',
          items: [
            'developer/mobile/arquitectura',
            'developer/mobile/stack',
          ],
        },
        'developer/setup',
        'developer/getting-started',
        'developer/architecture',
        'developer/project-structure',
        'developer/testing/unit-tests',
        'developer/deployment/build',
        'developer/resources',
      ],
    },
  ],
};

export default sidebars;
