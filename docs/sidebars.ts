import type {SidebarsConfig} from '@docusaurus/plugin-content-docs';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

/**
 * Creating a sidebar enables you to:
 - create an ordered group of docs
 - render a sidebar for each doc of that group
 - provide next/previous navigation

 The sidebars can be generated from the filesystem, or explicitly defined here.

 Create as many sidebars as you want.
 */
const sidebars: SidebarsConfig = {
  userSidebar: [
    {
      type: 'category',
      label: 'Guía de Usuario',
      items: [
        'user/intro',
        'user/getting-started',
        'user/features',
        {
          type: 'category',
          label: 'Funcionalidades',
          items: [
            'user/features/login',
            'user/features/dashboard',
            'user/features/settings',
          ],
        },
        'user/troubleshooting',
        'user/faq',
      ],
    },
  ],

  devSidebar: [
    {
      type: 'category',
      label: 'Guía de Desarrollador',
      items: [
        'developer/intro',
        'developer/setup',
        'developer/getting-started',
        'developer/architecture',
        'developer/project-structure',
        'developer/modules',
        'developer/database',
        {
          type: 'category',
          label: 'Testing',
          items: [
            'developer/testing/unit-tests',
            'developer/testing/integration-tests',
          ],
        },
        {
          type: 'category',
          label: 'Deployment',
          items: [
            'developer/deployment/build',
            'developer/deployment/release',
          ],
        },
        'developer/contributing',
        'developer/resources',
      ],
    },
  ],
};

export default sidebars;
