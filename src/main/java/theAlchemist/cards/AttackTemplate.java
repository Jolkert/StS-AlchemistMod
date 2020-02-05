package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.*;

public class AttackTemplate extends CustomCard
{// Template for a basic strike card
	public static final String ID = AlchemistMod.makeID(AttackTemplate.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("AttackTemplate.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int DAMAGE = 6;
	private static final int UPGRADE_PLUS_DMG = 3; //upgrade
	
	public AttackTemplate()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
		this.baseDamage = DAMAGE;
		
		this.tags.add(BaseModCardTags.BASIC_STRIKE); // If a strike, defend, or form card (like Wraith form, Demon form, Echo form, etc.) make sure they are tagged so they can function properly - Jolkert 2020-01-31
		this.tags.add(CardTags.STARTER_STRIKE); // Don't use tag if its not a strike defend or form - Aron 2020-02-01
												// Also if it's a heal, please tag it with CardTags.HEALING -Jolkert 2020-02-02
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
