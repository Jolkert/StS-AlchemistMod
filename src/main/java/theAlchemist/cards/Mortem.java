package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.enums.AlchemistCardTags;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Mortem extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Mortem.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Mortem.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
	private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;
	
	private static final int COST = 3;
	private static final int DAMAGE = 6;
	private static final int UPGRADE_PLUS_DMG = 2; //upgrade
	
	public static final int STAGE = 2;
	public static final AbstractElement[] CONSTITUENTS = {new Tremor(), new Glacies()};
	public static final AbstractElement[] REACTANTS = null;
	public static final AbstractElement[] PRODUCTS  = null;
	
	public Mortem()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Death", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseDamage = DAMAGE;
		this.exhaust = true;
		
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		for(AbstractCard ignored : player.exhaustPile.group)
			AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(player, damage, damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		
		super.use(player, monster); // Don't change this part if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			initializeDescription();
		}
	}
}
