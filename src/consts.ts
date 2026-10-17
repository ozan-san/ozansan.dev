export const SITE = {
  title: 'Ozan Şan',
  author: 'Ozan Şan',
  info: 'Android & Backend Developer',
  description: "Ozan Şan's personal website",
  url: 'https://ozansan.dev',
  since: 2019,
} as const;

export const NAV = [
  { name: 'Blog', href: '/posts/' },
  { name: 'About', href: '/about/' },
] as const;

export const SOCIALS = [
  { name: 'GitHub', href: 'https://github.com/ozan-san/', icon: 'github' },
  { name: 'LinkedIn', href: 'https://linkedin.com/in/ozan-san/', icon: 'linkedin' },
] as const;